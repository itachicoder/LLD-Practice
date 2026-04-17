package pu.com.ay.LldPracticeQ.Spotify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Album {
    private final String albumId;
    private final String title;
    private final String artistId;
    private final List<Song> songs;
 
    public Album(String albumId, String title, String artistId) {
        this.albumId  = albumId;
        this.title    = title;
        this.artistId = artistId;
        this.songs    = new ArrayList<>();
    }
 
    // Artist calls this when building the album
    public void addSong(Song song) {
        songs.add(song);
    }
 
    // --- Getters ---
    public String getAlbumId()    { return albumId; }
    public String getTitle()      { return title; }
    public String getArtistId()   { return artistId; }
    public int getSongCount()     { return songs.size(); }
 
    // Return defensive copy — callers can read but not mutate
    public List<Song> getSongs()  {
        return Collections.unmodifiableList(songs);
    }
 
    @Override
    public String toString() {
        return String.format("Album[%s | %s | %d songs]",
                albumId, title, songs.size());
    }
}
 