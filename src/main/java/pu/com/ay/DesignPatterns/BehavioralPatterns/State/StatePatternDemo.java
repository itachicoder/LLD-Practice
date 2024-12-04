package pu.com.ay.DesignPatterns.BehavioralPatterns.State;

// Context class
 class MediaPlayer {
    private State state;
    private boolean isPlaying = false;
    
    public MediaPlayer() {
        // Initial state is Ready
        this.state = new ReadyState(this);
    }
    
    public void changeState(State state) {
        this.state = state;
    }
    
    public State getState() {
        return state;
    }
    
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
    
    // Actions that can be performed on the media player
    public void clickPlay() {
        state.clickPlay();
    }
    
    public void clickLock() {
        state.clickLock();
    }
    
    public void clickNext() {
        state.clickNext();
    }
    
    public void clickPrevious() {
        state.clickPrevious();
    }
}

// State interface
 interface State {
    void clickPlay();
    void clickLock();
    void clickNext();
    void clickPrevious();
}

// Concrete state: Ready State
 class ReadyState implements State {
    private MediaPlayer player;
    
    public ReadyState(MediaPlayer player) {
        this.player = player;
    }
    
    @Override
    public void clickPlay() {
        player.setPlaying(true);
        System.out.println("Starting playback...");
        player.changeState(new PlayingState(player));
    }
    
    @Override
    public void clickLock() {
        System.out.println("Locking player controls...");
        player.changeState(new LockedState(player));
    }
    
    @Override
    public void clickNext() {
        System.out.println("Cannot skip - playback is not started");
    }
    
    @Override
    public void clickPrevious() {
        System.out.println("Cannot go back - playback is not started");
    }
}

// Concrete state: Playing State
 class PlayingState implements State {
    private MediaPlayer player;
    
    public PlayingState(MediaPlayer player) {
        this.player = player;
    }
    
    @Override
    public void clickPlay() {
        player.setPlaying(false);
        System.out.println("Pausing playback...");
        player.changeState(new ReadyState(player));
    }
    
    @Override
    public void clickLock() {
        System.out.println("Locking player controls...");
        player.changeState(new LockedState(player));
    }
    
    @Override
    public void clickNext() {
        System.out.println("Skipping to next track...");
    }
    
    @Override
    public void clickPrevious() {
        System.out.println("Returning to previous track...");
    }
}

// Concrete state: Locked State
 class LockedState implements State {
    private MediaPlayer player;
    
    public LockedState(MediaPlayer player) {
        this.player = player;
    }
    
    @Override
    public void clickPlay() {
        System.out.println("Player is locked - cannot play");
    }
    
    @Override
    public void clickLock() {
        if (player.isPlaying()) {
            player.changeState(new PlayingState(player));
            System.out.println("Unlocking player controls - resuming playback...");
        } else {
            player.changeState(new ReadyState(player));
            System.out.println("Unlocking player controls - ready state...");
        }
    }
    
    @Override
    public void clickNext() {
        System.out.println("Player is locked - cannot skip");
    }
    
    @Override
    public void clickPrevious() {
        System.out.println("Player is locked - cannot go back");
    }
}

// Demo class to test the implementation
public class StatePatternDemo {
    public static void main(String[] args) {
        MediaPlayer player = new MediaPlayer(); 
        
        // Testing various state transitions
        System.out.println("Testing player in Ready state:");
        player.clickPlay();  // Starts playing
        player.clickNext();  // Skips to next track
        
        System.out.println("\nTesting player in Playing state:");
        player.clickLock();  // Locks the player
        
        System.out.println("\nTesting player in Locked state:");
        player.clickPlay();  // Try to play while locked
        player.clickNext();  // Try to skip while locked
        player.clickLock();  // Unlock the player
        
        System.out.println("\nBack to Playing state:");
        player.clickPlay();  // Pause the playback
    }
}