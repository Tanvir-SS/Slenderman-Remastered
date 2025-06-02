package CMPT276.Group12.GameObjects.Actors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.Utility.Vector2D;

public class PlayerTest {
    private Player player;
    private static final int SPRITE_SIZE = 32;
    private static final int VISION_RADIUS_LARGE = 8;
    private static final int VISION_RADIUS_NORMAL = 4;

    @BeforeEach
    void setUp() {
        player = new Player(SPRITE_SIZE, VISION_RADIUS_LARGE, VISION_RADIUS_NORMAL);
        player.setPosition(new Vector2D(0, 0));
    }

    @Test
    void testInitialState() {
        assertTrue(player.isActive());
        assertEquals(0, player.getPages());
        assertEquals(VISION_RADIUS_NORMAL, player.getVisionRadius());
    }

    @Test
    void testMovement() {
        Vector2D initialPos = new Vector2D(player.getPosition());
        player.move(new Vector2D(0, 0));
        assertEquals(initialPos.getX(), player.getPosition().getX());
        assertEquals(initialPos.getY(), player.getPosition().getY());

        player.move(new Vector2D(1, 1));
        assertEquals(initialPos.getX() + 1, 1);
        assertEquals(initialPos.getY() + 1, player.getPosition().getY());
    }

    @Test
    void testPageCollection() {
        assertEquals(0, player.getPages());
        player.addPage();
        assertEquals(1, player.getPages());
        player.removePage();
        assertEquals(0, player.getPages());
    }

    @Test
    void testVisionRadius() {
        assertEquals(VISION_RADIUS_NORMAL, player.getVisionRadius());
        player.increaseVision();
        assertEquals(VISION_RADIUS_LARGE, player.getVisionRadius());
        player.reduceVision();
        assertEquals(VISION_RADIUS_NORMAL, player.getVisionRadius());
    }

    @Test
    void testTrapping() {
        assertFalse(player.isTrapped());
        player.trap();
        assertTrue(player.isTrapped());
        assertEquals(1, player.trapped);
        player.untrap();
        assertFalse(player.isTrapped());
    }

    @Test
    void testCannotMoveWhenTrapped() {
        Vector2D initialPos = player.getPosition();
        player.trap();
        assertThrows(IllegalStateException.class, () -> player.move(new Vector2D(1, 0)));
        assertEquals(initialPos.getX(), player.getPosition().getX());
        assertEquals(initialPos.getY(), player.getPosition().getY());
    }

    @Test
    void testClearPages() {
        player.addPage();
        player.addPage();
        assertEquals(2, player.getPages());
        player.clearPages();
        assertEquals(0, player.getPages());
    }

    @Test
    void testDie() {
        assertFalse(player.isDead());
        player.die();
        assertTrue(player.isDead());
        assertThrows(IllegalStateException.class, () -> player.die());
    }

    @Test
    void testTrap() {
        assertFalse(player.isTrapped());
        player.trap();
        assertTrue(player.isTrapped());
        assertThrows(IllegalStateException.class, () -> player.trap());
    }

    @Test
    void testUntrap() {
        player.trap();
        assertTrue(player.isTrapped());
        player.untrap();
        assertFalse(player.isTrapped());
        assertThrows(IllegalStateException.class, () -> player.untrap());
    }

    @Test
    void testIsDead() {
        assertFalse(player.isDead());
        player.die();
        assertTrue(player.isDead());
    }

    @Test
    void trappedThrowsException() {
        player.die();
        assertThrows(IllegalStateException.class, () -> player.trap());
    }

    @Test
    void untrapThrowsException() {
        player.die();
        assertThrows(IllegalStateException.class, () -> player.untrap());
    }

    @Test
    void isActiveTest() {
        assertTrue(player.isActive());
        player.die();
        assertFalse(player.isActive());
    }

    @Test
    void moveWhileTrappedTest() {
        player.trap();
        assertThrows(IllegalStateException.class, () -> player.move(new Vector2D(0, 0)));
    }

    @Test
    void moveWhilePlayerDeadTest() {
        player.die();
        assertThrows(IllegalStateException.class, () -> player.move(new Vector2D(0, 0)));
    }
}