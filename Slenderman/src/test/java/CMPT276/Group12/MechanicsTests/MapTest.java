package CMPT276.Group12.MechanicsTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.GameObjects.Actors.Player;
import CMPT276.Group12.GameObjects.Environment.ExitTile;
import CMPT276.Group12.GameObjects.Environment.Ground;
import CMPT276.Group12.GameObjects.Environment.StartTile;
import CMPT276.Group12.GameObjects.Environment.Wall;
import CMPT276.Group12.Mechanics.Map;
import CMPT276.Group12.Utility.Vector2D;

public class MapTest {
    private Map map;

    @BeforeEach
    public void setUp() {
        map = new Map(32, 32);
        Player player = new Player(map.SPRITE_SIZE, 6, 4);
        map.buildMap(player);
    }

    @Test
    public void testSetExit() {
        boolean exitFound = false;
        for (int x = 0; x < map.cells.length; x++) {
            for (int y = 0; y < map.cells[0].length; y++) {
                if (map.cells[x][y].tile instanceof ExitTile) {
                    exitFound = true;
                    break;
                }
            }
            if (exitFound)
                break;
        }
        assertTrue(exitFound);
    }

    @Test
    public void testGenerateWalledMap() {
        boolean validPlacements = true;
        for (int x = 0; x < map.cells.length; x++) {
            for (int y = 0; y < map.cells[0].length; y++) {
                if (x == 0 || x == map.cells.length - 1 || y == 0 || y == map.cells[0].length - 1) {
                    if (!(map.cells[x][y].tile instanceof Wall || map.cells[x][y].tile instanceof ExitTile)) {
                        validPlacements = false;
                        break;
                    }
                }
            }
            if (validPlacements)
                break;
        }
        assertTrue(validPlacements);
    }

    @Test
    public void testSetStartTile() {
        boolean startFound = false;
        for (int x = 0; x < map.cells.length; x++) {
            for (int y = 0; y < map.cells[0].length; y++) {
                if (map.cells[x][y].tile instanceof StartTile) {
                    startFound = true;
                    break;
                }
            }
            if (startFound)
                break;
        }
        assertTrue(startFound);
    }

    @Test
    public void testGetEmptyGroundTile() {
        Vector2D emptyPos = map.getEmptyGroundTile(new Vector2D(0, 0));

        if (map.cells[emptyPos.getX()][emptyPos.getY()].tile instanceof Ground)
            assertTrue(map.cells[emptyPos.getX()][emptyPos.getY()].getInteractable() == null);

        else // is not Ground, this is not empty
            assertTrue(false);

    }
}
