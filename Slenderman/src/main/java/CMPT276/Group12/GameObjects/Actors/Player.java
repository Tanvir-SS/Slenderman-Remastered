package CMPT276.Group12.GameObjects.Actors;

import CMPT276.Group12.GameObjects.AnimatedGameObject;
import CMPT276.Group12.Mechanics.StateMachine;
import CMPT276.Group12.States.PlayerState;
import CMPT276.Group12.Utility.Vector2D;

/**
 * The Player class represents a player in the game, extending the
 * AnimatedGameObject class.
 * It manages the player's vision radius, collected pages, and state.
 */
public class Player extends AnimatedGameObject<PlayerState> {
    private static String[] sprite32pxPaths = {
            "/sprites/mono_set/player/normal1.png",
            "/sprites/mono_set/player/normal2.png",
            "/sprites/mono_set/player/normal3.png",
    };
    /**
     * The radius of the player's vision when it is in the large state.
     */
    private final int visionRadiusLarge;
    /**
     * The normal vision radius of the player.
     */
    private final int visionRadiusNormal;
    /**
     * The radius of the player's vision, determining how far the player can see.
     */
    private int visionRadius;
    /**
     * The number of pages collected by the player.
     */
    private int pages;

    public int trapped;

    /**
     * Constructs a Player object with specified vision radii.
     *
     * @param visionRadiusLarge  the larger vision radius for the player
     * @param visionRadiusNormal the normal vision radius for the player
     */
    public Player(int spriteSize, int visionRadiusLarge, int visionRadiusNormal) {
        super(spriteSize, sprite32pxPaths);
        this.visionRadiusLarge = visionRadiusLarge;
        this.visionRadiusNormal = visionRadiusNormal;
        this.visionRadius = visionRadiusNormal;
        this.pages = 0;
        stateMachine = new StateMachine<PlayerState>(PlayerState.ACTIVE);
    }

    /**
     * Resets the player's vision radius to the normal vision radius.
     */
    public void reduceVision() {
        this.visionRadius = visionRadiusNormal;
    }

    /**
     * Increases the player's vision radius to a larger predefined value.
     */
    public void increaseVision() {
        this.visionRadius = visionRadiusLarge;
    }

    /**
     * Returns the vision radius of the player.
     *
     * @return the vision radius of the player
     */
    public int getVisionRadius() {
        return this.visionRadius;
    }

    /**
     * Increments the number of pages collected by the player by one.
     */
    public void addPage() {
        this.pages++;
    }

    /**
     * Retrieves the number of pages collected by the player.
     *
     * @return the number of pages collected.
     */
    public int getPages() {
        return this.pages;
    }

    /**
     * Decrements the number of pages after player steps on bear trap
     */
    public void removePage() {
        this.pages--;
    }

    public void clearPages() {
        this.pages = 0;
    }

    /**
     * Changes the player's state to DEAD. If the player is already dead,
     * an IllegalStateException is thrown.
     *
     * @throws IllegalStateException if the player is already in the DEAD state.
     */
    public void die() {
        if (this.isDead()) {
            throw new IllegalStateException("Player is already dead.");
        }

        super.stateMachine.setState(PlayerState.DEAD);
    }

    /**
     * Traps the player by changing their state to TRAPPED.
     * 
     * @throws IllegalStateException if the player is already dead or already
     *                               trapped.
     */
    public void trap() {
        if (this.isDead()) {
            throw new IllegalStateException("Player is dead and cannot be trapped.");
        }
        if (this.isTrapped()) {
            throw new IllegalStateException("Player is already trapped.");
        }

        super.stateMachine.setState(PlayerState.TRAPPED);
        trapped++;
    }

    /**
     * Changes the player's state from TRAPPED to ACTIVE.
     * 
     * @throws IllegalStateException if the player is not currently in the TRAPPED
     *                               state.
     */
    public void untrap() {
        if (this.isDead()) {
            throw new IllegalStateException("Player is dead and cannot be untrapped.");
        }

        if (!this.isTrapped()) {
            throw new IllegalStateException("Player is not trapped.");
        }

        super.stateMachine.setState(PlayerState.ACTIVE);
    }

    /**
     * Checks if the player is dead.
     *
     * @return true if the player's state is DEAD, false otherwise.
     */
    public boolean isDead() {
        return super.stateMachine.getState() == PlayerState.DEAD;
    }

    /**
     * Checks if the player is trapped.
     *
     * @return true if the player's state is TRAPPED, false otherwise.
     */
    public boolean isTrapped() {
        return super.stateMachine.getState() == PlayerState.TRAPPED;
    }

    /**
     *
     * @return true if the player's state is ACTIVE, false otherwise.
     */
    public boolean isActive() {
        return super.stateMachine.getState() == PlayerState.ACTIVE;
    }

    /**
     * Moves the player in the specified direction.
     * 
     * @param direction The direction vector in which the player should move.
     * @throws IllegalStateException if the player is dead or trapped and cannot
     *                               move.
     */
    public void move(Vector2D direction) {
        if (this.isDead()) {
            throw new IllegalStateException("Player is Dead. Cannot move");
        }

        if (this.isTrapped()) {
            throw new IllegalStateException("Player is Trapped. Cannot move");
        }

        position.add(direction);
    }
}
