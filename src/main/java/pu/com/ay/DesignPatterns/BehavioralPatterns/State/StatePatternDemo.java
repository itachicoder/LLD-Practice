package pu.com.ay.DesignPatterns.BehavioralPatterns.State;

/**
 * STATE DESIGN PATTERN
 * 
 * The State pattern allows an object to alter its behavior when its internal state changes.
 * This pattern encapsulates state-specific behavior into separate state classes and delegates
 * behavior to the current state object instead of implementing state-specific behavior directly.
 * 
 * Key components:
 * 1. Context (MediaPlayer) - Maintains a reference to a state object and delegates state-specific behavior
 * 2. State Interface - Defines the interface for all concrete states
 * 3. Concrete States - Implement the State interface for specific states
 */

// Context class - Maintains the current state and delegates state-specific behavior
class MediaPlayer {
    private State state;
    private boolean isPlaying = false;
    
    public MediaPlayer() {
        // Initial state is Ready - this establishes the starting state
        this.state = new ReadyState(this);
    }
    
    // Method to transition between states
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
    // Each action is delegated to the current state object
    public void clickPlay() {
        state.clickPlay();  // Delegate to current state
    }
    
    public void clickLock() {
        state.clickLock();  // Delegate to current state
    }
    
    public void clickNext() {
        state.clickNext();  // Delegate to current state
    }
    
    public void clickPrevious() {
        state.clickPrevious();  // Delegate to current state
    }
}

// State interface - Defines the methods that all concrete states must implement
interface State {
    void clickPlay();
    void clickLock();
    void clickNext();
    void clickPrevious();
}

// Concrete state: Ready State
// Represents the player when it's ready to play but not currently playing
class ReadyState implements State {
    private MediaPlayer player;
    
    public ReadyState(MediaPlayer player) {
        this.player = player;  // Reference to context for state transitions
    }
    
    @Override
    public void clickPlay() {
        player.setPlaying(true);
        System.out.println("Starting playback...");
        // State transition: Ready -> Playing
        player.changeState(new PlayingState(player));
    }
    
    @Override
    public void clickLock() {
        System.out.println("Locking player controls...");
        // State transition: Ready -> Locked
        player.changeState(new LockedState(player));
    }
    
    @Override
    public void clickNext() {
        // State-specific behavior: Can't skip when not playing
        System.out.println("Cannot skip - playback is not started");
    }
    
    @Override
    public void clickPrevious() {
        // State-specific behavior: Can't go back when not playing
        System.out.println("Cannot go back - playback is not started");
    }
}

// Concrete state: Playing State
// Represents the player when it's actively playing media
class PlayingState implements State {
    private MediaPlayer player;
    
    public PlayingState(MediaPlayer player) {
        this.player = player;  // Reference to context for state transitions
    }
    
    @Override
    public void clickPlay() {
        player.setPlaying(false);
        System.out.println("Pausing playback...");
        // State transition: Playing -> Ready
        player.changeState(new ReadyState(player));
    }
    
    @Override
    public void clickLock() {
        System.out.println("Locking player controls...");
        // State transition: Playing -> Locked
        player.changeState(new LockedState(player));
    }
    
    @Override
    public void clickNext() {
        // State-specific behavior: Can skip to next track when playing
        System.out.println("Skipping to next track...");
    }
    
    @Override
    public void clickPrevious() {
        // State-specific behavior: Can go back to previous track when playing
        System.out.println("Returning to previous track...");
    }
}

// Concrete state: Locked State
// Represents the player when controls are locked
class LockedState implements State {
    private MediaPlayer player;
    
    public LockedState(MediaPlayer player) {
        this.player = player;  // Reference to context for state transitions
    }
    
    @Override
    public void clickPlay() {
        // State-specific behavior: Can't play when locked
        System.out.println("Player is locked - cannot play");
    }
    
    @Override
    public void clickLock() {
        // Unlocking the player - returns to previous state (Playing or Ready)
        if (player.isPlaying()) {
            // State transition: Locked -> Playing (if was playing before)
            player.changeState(new PlayingState(player));
            System.out.println("Unlocking player controls - resuming playback...");
        } else {
            // State transition: Locked -> Ready (if was not playing before)
            player.changeState(new ReadyState(player));
            System.out.println("Unlocking player controls - ready state...");
        }
    }
    
    @Override
    public void clickNext() {
        // State-specific behavior: Can't skip when locked
        System.out.println("Player is locked - cannot skip");
    }
    
    @Override
    public void clickPrevious() {
        // State-specific behavior: Can't go back when locked
        System.out.println("Player is locked - cannot go back");
    }
}

/**
 * Demo class to test the implementation
 * 
 * STATE PATTERN BENEFITS:
 * 1. Single Responsibility Principle: Each state's behavior is encapsulated in its own class
 * 2. Open/Closed Principle: New states can be added without changing existing state classes
 * 3. Eliminates complex conditional statements for different states
 * 4. Makes state transitions explicit
 * 
 * WHEN TO USE:
 * - When an object's behavior depends on its state and must change at runtime
 * - When operations have large, multipart conditional statements that depend on the object's state
 */
public class StatePatternDemo {
    public static void main(String[] args) {
        MediaPlayer player = new MediaPlayer();  // Player starts in Ready state
        
        // Testing various state transitions
        System.out.println("Testing player in Ready state:");
        player.clickPlay();  // Starts playing -> transitions to Playing state
        player.clickNext();  // Skips to next track (works in Playing state)
        
        System.out.println("\nTesting player in Playing state:");
        player.clickLock();  // Locks the player -> transitions to Locked state
        
        System.out.println("\nTesting player in Locked state:");
        player.clickPlay();  // Try to play while locked (no state change)
        player.clickNext();  // Try to skip while locked (no state change)
        player.clickLock();  // Unlock the player -> transitions back to Playing state
        
        System.out.println("\nBack to Playing state:");
        player.clickPlay();  // Pause the playback -> transitions to Ready state
        
        // STATE TRANSITIONS DEMONSTRATED:
        // Ready -> Playing -> Locked -> Playing -> Ready
    }
}