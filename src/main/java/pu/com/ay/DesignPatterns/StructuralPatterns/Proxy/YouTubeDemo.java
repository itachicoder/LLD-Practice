package pu.com.ay.DesignPatterns.StructuralPatterns.Proxy;

// 1. Service Interface

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Record for Video instead of class (Java 16+ feature)
record Video(String videoId, String title, String data) {
    @Override
    public String toString() {
        return "Video{" +
               "videoId='" + videoId + '\'' +
               ", title='" + title + '\'' +
               ", dataSize='" + data.length() + " bytes'" +
               '}';
    }
}

// Service Interface
sealed interface YouTubeService permits RealYouTubeService, YouTubeServiceProxy {
    Video getVideo(String videoId);
    List<Video> listVideos(String channelId);
}

// Real Service Implementation
final class RealYouTubeService implements YouTubeService {
    @Override
    public Video getVideo(String videoId) {
        System.out.println("Downloading video from YouTube: " + videoId);
        simulateNetworkCall();
        return new Video(videoId, "Video " + videoId, "Sample video data for " + videoId);
    }
    
    @Override
    public List<Video> listVideos(String channelId) {
        System.out.println("Fetching video list from YouTube for channel: " + channelId);
        simulateNetworkCall();
        return List.of(
            new Video("1", "First Video", "Data 1"),
            new Video("2", "Second Video", "Data 2"),
            new Video("3", "Third Video", "Data 3")
        );
    }
    
    private void simulateNetworkCall() {
        try {
            Thread.sleep(2000); // Simulate slow network
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Network call interrupted", e);
        }
    }
}

// Proxy Implementation
final class YouTubeServiceProxy implements YouTubeService {
    private final YouTubeService realService;
    private final Map<String, Video> videoCache;
    private final Map<String, List<Video>> channelCache;
    
    public YouTubeServiceProxy() {
        this.realService = new RealYouTubeService();
        this.videoCache = new HashMap<>();
        this.channelCache = new HashMap<>();
    }
    
    @Override
    public Video getVideo(String videoId) {
        return videoCache.computeIfAbsent(videoId, id -> {
            System.out.println("Cache miss for video: " + id);
            return realService.getVideo(id);
        });
    }
    
    @Override
    public List<Video> listVideos(String channelId) {
        return channelCache.computeIfAbsent(channelId, id -> {
            System.out.println("Cache miss for channel: " + id);
            return realService.listVideos(id);
        });
    }
    
    public void clearCache() {
        System.out.println("Clearing all caches");
        videoCache.clear();
        channelCache.clear();
    }
}

// Main Demo Class
public class YouTubeDemo {
    public static void main(String[] args) {
        // Using var (Java 10+ feature)
        var youtubeService = new YouTubeServiceProxy();
        
        // Test video download with text blocks (Java 15+ feature)
        System.out.println("""
            
            First request for video 'abc':""");
        var video1 = youtubeService.getVideo("abc");
        System.out.println(video1);
        
        System.out.println("""
            
            Second request for same video 'abc' (should be cached):""");
        var video2 = youtubeService.getVideo("abc");
        System.out.println(video2);
        
        // Test channel listing
        System.out.println("""
            
            First request for channel 'channel1':""");
        var channelVideos1 = youtubeService.listVideos("channel1");
        System.out.println("Retrieved %d videos".formatted(channelVideos1.size()));
        
        System.out.println("""
            
            Second request for same channel (should be cached):""");
        var channelVideos2 = youtubeService.listVideos("channel1");
        System.out.println("Retrieved %d videos".formatted(channelVideos2.size()));
        
        // Clear cache and try again
        System.out.println("""
            
            Clearing cache and requesting video 'abc' again:""");
        youtubeService.clearCache();
        var video3 = youtubeService.getVideo("abc");
        System.out.println(video3);
        
        // Demonstrate pattern matching instanceof (Java 16+ feature)
        if (youtubeService instanceof YouTubeServiceProxy proxy) {
            proxy.clearCache();
        }
    }
}