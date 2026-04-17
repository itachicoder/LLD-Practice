package pu.com.ay.LldPracticeQ.Spotify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
 
    // All fields are set once via Builder, then only songs list mutates
    private final String playlistId;
    private final String name;
    private final String ownerId;
    private final boolean isPublic;
    private final String description;
    private final List<Song> songs;
    private final LocalDateTime createdAt;
 
    // Private constructor — only Builder can call this
    private Playlist(Builder builder) {
        this.playlistId  = builder.playlistId;
        this.name        = builder.name;
        this.ownerId     = builder.ownerId;
        this.isPublic    = builder.isPublic;
        this.description = builder.description;
        this.songs       = builder.songs;
        this.createdAt   = LocalDateTime.now();
    }
 
    // --- Post-construction mutations (allowed after building) ---
 
    public void addSong(Song song) {
        songs.add(song);
    }
 
    public void removeSong(String songId) {
        // songs.removeIf(s -> s.getSongId().equals(songId));
    }
 
    // --- Getters ---
    public String getPlaylistId()  { return playlistId; }
    public String getName()        { return name; }
    public String getOwnerId()     { return ownerId; }
    public boolean isPublic()      { return isPublic; }
    public String getDescription() { return description; }
    public int getSongCount()      { return songs.size(); }
 
    public List<Song> getSongs() {
        return Collections.unmodifiableList(songs);
    }
 
    @Override
    public String toString() {
        return String.format("Playlist[%s | \"%s\" | %d songs | public=%s]",
                playlistId, name, songs.size(), isPublic);
    }
 
    // ============================================================
    //  BUILDER — inner static class
    //
    //  Usage:
    //    Playlist p = new Playlist.Builder("PL1", "Chill", "U1")
    //                     .makePublic()
    //                     .withDescription("Evening music")
    //                     .addSong(song1)
    //                     .build();
    // ============================================================
    public static class Builder {
 
        // Required fields
        private final String playlistId;
        private final String name;
        private final String ownerId;
 
        // Optional fields — defaults defined here
        private boolean isPublic    = false;
        private String description  = "";
        private List<Song> songs    = new ArrayList<>();
 
        // Constructor takes only required fields
        public Builder(String playlistId, String name, String ownerId) {
            this.playlistId = playlistId;
            this.name       = name;
            this.ownerId    = ownerId;
        }
 
        // Each optional setter returns 'this' so calls can be chained
        public Builder makePublic() {
            this.isPublic = true;
            return this;
        }
 
        public Builder withDescription(String desc) {
            this.description = desc;
            return this;
        }
 
        public Builder addSong(Song song) {
            this.songs.add(song);
            return this;
        }
 
        // Terminal method — creates the immutable Playlist
        public Playlist build() {
            return new Playlist(this);
        }
    }
}
