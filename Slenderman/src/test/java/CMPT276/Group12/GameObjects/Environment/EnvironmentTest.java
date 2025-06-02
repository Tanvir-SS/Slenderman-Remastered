package CMPT276.Group12.GameObjects.Environment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.Mechanics.Cell;
import CMPT276.Group12.Mechanics.Map;

public class EnvironmentTest {
    private ExitTile exitTile;
    private Ground ground;
    private StartTile startTile;
    private Wall wall;

    private final int test_SPRITE_SIZE = 32;
    private final int test_CELL_ROWS = 1;
    private final int test_CELL_COLUMNS = 1;

    @BeforeEach
    void setUp() {
        new Map(test_CELL_ROWS, test_CELL_COLUMNS);

        exitTile = new ExitTile(test_SPRITE_SIZE, 0);
        ground = new Ground(test_SPRITE_SIZE, 0);
        startTile = new StartTile(test_SPRITE_SIZE, 0);
        wall = new Wall(test_SPRITE_SIZE, 0);
    }

    @Test
    void testExitTile() {
        assertEquals(false, exitTile.isSolid());
    }

    @Test
    void testGroundTile() {
        assertEquals(false, ground.isSolid());
    }

    @Test
    void testStartTile() {
        Cell currentCell = new Cell(startTile);

        assertNull(currentCell.tile.getSprites());
        assertEquals(false, startTile.isSolid());
    }

    @Test
    void testWallTile() {
        assertEquals(true, wall.isSolid());
    }

}
