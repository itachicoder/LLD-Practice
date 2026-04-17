package pu.com.ay.LldPracticeQ.Spotify;


public class Song {
    private final String songId;
    private final String title;
    private final String artistId;      // reference by ID, not object (loose coupling)
    private final int durationSeconds;
    // private final Genre genre;
    private int playCount;              // mutable — gets incremented on play
 
    // Constructor
    public Song(String songId, String title, String artistId,
                int durationSeconds) {
        this.songId          = songId;
        this.title           = title;
        this.artistId        = artistId;
        this.durationSeconds = durationSeconds;
        // this.genre           = genre;
        this.playCount       = 0;
    }
 
    // Called by MusicPlayer when this song is played
    public void incrementPlayCount() {
        this.playCount++;
    }
 
    // --- Getters ---
    public String getSongId()        { return songId; }
    public String getTitle()         { return title; }
    public String getArtistId()      { return artistId; }
    public int getDurationSeconds()  { return durationSeconds; }
    public int getPlayCount()        { return playCount; }
 
    @Override
    public String toString() {
        return String.format("Song[%s | %s | %s | %ds]",
                songId, title, durationSeconds);
    }
}
