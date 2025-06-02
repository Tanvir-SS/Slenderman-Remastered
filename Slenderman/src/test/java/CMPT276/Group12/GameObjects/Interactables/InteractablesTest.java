package CMPT276.Group12.GameObjects.Interactables;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import CMPT276.Group12.GameObjects.Actors.Player;
import CMPT276.Group12.Mechanics.Game;

public class InteractablesTest {
    private BearTrap bearTrap;
    private EnemyFreeze enemyFreeze;
    private VisionIncrease visionIncrease;
    private Page page;
    private Player player;
    private Game game;

    @BeforeEach
    void setUp() {
        player = new Player(32, 8, 4);
        game = new Game();
        bearTrap = new BearTrap(32, game);
        enemyFreeze = new EnemyFreeze(32);
        visionIncrease = new VisionIncrease(32, 3);
        page = new Page(32);
    }

    @Test
    void testBearTrapInitialState() {
        assertEquals("bearTrap", bearTrap.getName());
    }

    @Test
    @Timeout(5) // Test should complete within 5 seconds
    void testBearTrapInteraction() {
        // Test trapping player mechanics
        player.addPage();
        int initialPages = player.getPages();

        assertFalse(player.isTrapped());
        bearTrap.interact(player);
        assertTrue(player.isTrapped());
        assertEquals(initialPages - 1, player.getPages());
    }

    @Test
    void testEnemyFreezeInitialState() {
        assertEquals("enemyFreeze", enemyFreeze.getName());
    }

    @Test
    void testEnemyFreezeInteraction() {
        // Note: This test might need to be expanded once freezeEnemy() is implemented
        enemyFreeze.interact(player);
    }

    @Test
    void testVisionIncreaseInitialState() {
        assertEquals("visionIncrease", visionIncrease.getName());
        assertEquals(3, visionIncrease.increaseVision());
    }

    @Test
    void testVisionIncreaseInteraction() {
        player.getVisionRadius();
        visionIncrease.interact(player);
        // Note: This test might need to be expanded once vision increase is fully
        // implemented
    }

    @Test
    void testPageInitialState() {
        assertEquals("page", page.getName());
    }

    @Test
    void testPageInteraction() {
        int initialPages = player.getPages();
        page.interact(player);
        assertEquals(initialPages + 1, player.getPages());
    }
}
