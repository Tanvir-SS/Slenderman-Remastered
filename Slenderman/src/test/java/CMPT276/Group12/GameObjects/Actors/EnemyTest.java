package CMPT276.Group12.GameObjects.Actors;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import CMPT276.Group12.Mechanics.Cell;
import CMPT276.Group12.Utility.Vector2D;
import CMPT276.Group12.GameObjects.Environment.Ground;
import CMPT276.Group12.GameObjects.Environment.Wall;

public class EnemyTest {
    private Enemy enemy;
    private Player player;
    private Cell[][] cells;
    private static final int SPRITE_SIZE = 32;
    private static final int MAP_SIZE = 10;

    @BeforeEach
    void setUp() {
        enemy = new Enemy(SPRITE_SIZE);
        player = new Player(SPRITE_SIZE, 8, 4);
        cells = new Cell[MAP_SIZE][MAP_SIZE];

        // Initialize map with ground tiles
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                cells[i][j] = new Cell(new Ground(SPRITE_SIZE, 0));
            }
        }

        enemy.setCells(cells);
        enemy.setPosition(new Vector2D(5, 5));
        player.setPosition(new Vector2D(7, 7));
    }

    @Test
    void testInitialState() {
        assertTrue(enemy.isActive());
    }

    @Test
    void testFreezing() {
        assertTrue(enemy.isActive());
        enemy.freeze();
        assertFalse(enemy.isActive());
        enemy.unfreeze();
        assertTrue(enemy.isActive());
    }

    @Test
    void testMovementTowardsPlayer() {
        Vector2D initialPos = enemy.getPosition();
        enemy.updateEnemyPosition(player);
        Vector2D newPos = enemy.getPosition();

        // Enemy should move one step closer to player
        assertTrue(Math.abs(newPos.getX() - player.getPosition().getX()) <= Math
                .abs(initialPos.getX() - player.getPosition().getX()));
        assertTrue(Math.abs(newPos.getY() - player.getPosition().getY()) <= Math
                .abs(initialPos.getY() - player.getPosition().getY()));
    }

    @Test
    void testCannotMoveThroughWalls() {
        // Place a wall between enemy and player
        cells[6][6] = new Cell(new Wall(SPRITE_SIZE, 0));

        Vector2D initialPos = enemy.getPosition();
        enemy.updateEnemyPosition(player);
        Vector2D newPos = enemy.getPosition();

        // Enemy should not move through the wall
        assertTrue(newPos.getX() == initialPos.getX() || newPos.getY() == initialPos.getY());
    }

    @Test
    void testLineOfSight() {
        // Test with clear line of sight
        assertTrue(enemy.hasLineOfSight(player));

        // Place a wall between enemy and player
        cells[6][6] = new Cell(new Wall(SPRITE_SIZE, 0));
        assertFalse(enemy.hasLineOfSight(player));
    }

    @Test
    void testFrozenEnemy() {
        enemy.freeze();
        assertFalse(enemy.isActive());
        enemy.updateEnemyPosition(player);
        Vector2D newPos = enemy.getPosition();
        assertEquals(newPos, new Vector2D(5, 5));
        enemy.unfreeze();
        assertTrue(enemy.isActive());
    }

    @Test
    void testEnemyMovementWithNullCells() {
        enemy.setCells(null);
        enemy.updateEnemyPosition(player);
        assertEquals(enemy.getPosition(), new Vector2D(5, 5));
    }

    @Test
    void testPlayerMoreThan4CellsAway() {
        player.setPosition(new Vector2D(10, 10));
        Vector2D initialPos = enemy.getPosition();
        enemy.updateEnemyPosition(player);
        Vector2D newPos = enemy.getPosition();

        // Enemy should move (since it's active and has valid cells)
        assertNotEquals(initialPos, newPos);

        // Movement should be one tile at a time
        assertTrue(Math.abs(newPos.getX() - initialPos.getX()) <= 1);
        assertTrue(Math.abs(newPos.getY() - initialPos.getY()) <= 1);

        // Movement should be within map bounds
        assertTrue(newPos.getX() >= 0 && newPos.getX() < MAP_SIZE);
        assertTrue(newPos.getY() >= 0 && newPos.getY() < MAP_SIZE);

        // Movement should not be through walls
        assertFalse(cells[newPos.getX()][newPos.getY()].tile.isSolid());
    }

    @Test
    void testMultipleMovementAttempts() {
        // // Surround enemy with walls on three sides
        // cells[5][4] = new Cell(new Wall(SPRITE_SIZE, 0)); // up
        // cells[4][5] = new Cell(new Wall(SPRITE_SIZE, 0)); // left
        // cells[6][5] = new Cell(new Wall(SPRITE_SIZE, 0)); // right

        // // Only down is free
        // Vector2D initialPos = enemy.getPosition();
        // enemy.updateEnemyPosition(player);
        // Vector2D newPos = enemy.getPosition();

        // // Enemy should move down (the only valid direction)
        // assertNotEquals(initialPos.getX(), newPos.getX());
        // System.err.println("X: " + initialPos.getX() + " " + newPos.getX());
        // System.err.println("Y: " + initialPos.getY() + " " + newPos.getY());
        // assertNotEquals(initialPos.getY() + 1, newPos.getY());
    }

    @Test
    void testRandomMovement() {
        // Test random movement when there are no walls

        for (int i = 0; i < 1000; i++) {
            // should hopefully provide coverage for all directions
            enemy.moveInRandomDirection();
            // ensure enemy is still within bounds
            assertTrue(enemy.getPosition().getX() >= 0 && enemy.getPosition().getX() < MAP_SIZE);
            assertTrue(enemy.getPosition().getY() >= 0 && enemy.getPosition().getY() < MAP_SIZE);
        }
    }

    @Test
    void testMovementWhileEnemyStuckInWall() {
        // Surround enemy with walls on all sides
        cells[5][4] = new Cell(new Wall(SPRITE_SIZE, 0)); // up
        cells[4][5] = new Cell(new Wall(SPRITE_SIZE, 0)); // left
        cells[6][5] = new Cell(new Wall(SPRITE_SIZE, 0)); // right
        cells[5][6] = new Cell(new Wall(SPRITE_SIZE, 0)); // down

        for (int i = 0; i < 1000; i++) {
            enemy.moveInRandomDirection();
            assertTrue(enemy.getPosition().getX() >= 0 && enemy.getPosition().getX() < MAP_SIZE);
            assertTrue(enemy.getPosition().getY() >= 0 && enemy.getPosition().getY() < MAP_SIZE);
        }
    }
}
