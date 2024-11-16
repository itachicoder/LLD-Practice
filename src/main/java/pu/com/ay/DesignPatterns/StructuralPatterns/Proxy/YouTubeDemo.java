package pu.com.ay.DesignPatterns.StructuralPatterns.Proxy;

// 1. Service Interface

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface YouTubeService {
    Video getVideo(String videoId);
    List<Video> listVideos(String channelId);
}

// 2. Value Object for Video
class Video {
    private String videoId;
    private String title;
    private String data;  // This would be the actual video data in real implementation
    
    public Video(String videoId, String title, String data) {
        this.videoId = videoId;
        this.title = title;
        this.data = data;
    }
    
    // Getters
    public String getVideoId() { return videoId; }
    public String getTitle() { return title; }
    public String getData() { return data; }
    
    @Override
    public String toString() {
        return "Video{" +
               "videoId='" + videoId + '\'' +
               ", title='" + title + '\'' +
               ", dataSize='" + data.length() + " bytes'" +
               '}';
    }
}

// 3. Real Service Implementation
class RealYouTubeService implements YouTubeService {
    @Override
    public Video getVideo(String videoId) {
        // Simulate network request
        System.out.println("Downloading video from YouTube: " + videoId);
        slowNetworkCall();  // Simulate slow network
        
        // In real implementation, this would actually download from YouTube
        return new Video(
            videoId,
            "Video " + videoId,
            "Sample video data for " + videoId
        );
    }
    
    @Override
    public List<Video> listVideos(String channelId) {
        // Simulate network request
        System.out.println("Fetching video list from YouTube for channel: " + channelId);
        slowNetworkCall();  // Simulate slow network
        
        // Return dummy list of videos
        return Arrays.asList(
            new Video("1", "First Video", "Data 1"),
            new Video("2", "Second Video", "Data 2"),
            new Video("3", "Third Video", "Data 3")
        );
    }
    
    private void slowNetworkCall() {
        try {
            Thread.sleep(2000); // Simulate slow network
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 4. Proxy Implementation with Caching
class YouTubeServiceProxy implements YouTubeService {
    private YouTubeService realService;
    private Map<String, Video> videoCache;
    private Map<String, List<Video>> channelCache;
    
    public YouTubeServiceProxy() {
        this.realService = new RealYouTubeService();
        this.videoCache = new HashMap<>();
        this.channelCache = new HashMap<>();
    }
    
    @Override
    public Video getVideo(String videoId) {
        // Check if video exists in cache
        if (videoCache.containsKey(videoId)) {
            System.out.println("Returning cached video: " + videoId);
            return videoCache.get(videoId);
        }
        
        // If not in cache, get from real service and cache it
        Video video = realService.getVideo(videoId);
        videoCache.put(videoId, video);
        return video;
    }
    
    @Override
    public List<Video> listVideos(String channelId) {
        // Check if channel list exists in cache
        if (channelCache.containsKey(channelId)) {
            System.out.println("Returning cached channel list: " + channelId);
            return channelCache.get(channelId);
        }
        
        // If not in cache, get from real service and cache it
        List<Video> videos = realService.listVideos(channelId);
        channelCache.put(channelId, videos);
        return videos;
    }
    
    // Method to clear cache if needed
    public void clearCache() {
        System.out.println("Clearing all caches");
        videoCache.clear();
        channelCache.clear();
    }
}

// 5. Demo Client Application
public class YouTubeDemo {
    public static void main(String[] args) {
        // Create proxy instance
        YouTubeService youtubeService = new YouTubeServiceProxy();
        
        // Test video download
        System.out.println("First request for video 'abc':");
        Video video1 = youtubeService.getVideo("abc");
        System.out.println(video1);
        
        System.out.println("\nSecond request for same video 'abc' (should be cached):");
        Video video2 = youtubeService.getVideo("abc");
        System.out.println(video2);
        
        // Test channel listing
        System.out.println("\nFirst request for channel 'channel1':");
        List<Video> channelVideos1 = youtubeService.listVideos("channel1");
        System.out.println("Retrieved " + channelVideos1.size() + " videos");
        
        System.out.println("\nSecond request for same channel (should be cached):");
        List<Video> channelVideos2 = youtubeService.listVideos("channel1");
        System.out.println("Retrieved " + channelVideos2.size() + " videos");
        
        // Clear cache and try again
        System.out.println("\nClearing cache and requesting video 'abc' again:");
        ((YouTubeServiceProxy) youtubeService).clearCache();
        Video video3 = youtubeService.getVideo("abc");
        System.out.println(video3);
    }
}