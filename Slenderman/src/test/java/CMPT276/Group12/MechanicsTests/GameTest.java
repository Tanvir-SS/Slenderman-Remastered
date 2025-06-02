package CMPT276.Group12.MechanicsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.GameObjects.Environment.Ground;
import CMPT276.Group12.Mechanics.Game;
import CMPT276.Group12.Utility.Vector2D;

public class GameTest {
    private static Game game;

    @BeforeAll
    static void seUp() {
        game = new Game();
    }

    @Test 
    void testSetEnemies() {
        assertTrue(game.getEnemies().size() > 0);
    }

    @Test
    void testSetInteractables() {
        assertTrue(game.getInteractables().length > 0);
        assertTrue(game.getInteractables()[0] != null);
    }

    @Test
    void testScore() {
        game.addBonus(10);
        assertTrue(game.getScore() > 0);
    }

    @Test
    void testGetPagesGetStage() {
        assertTrue(game.getStage() >= game.getPages() && game.getPages() >= 0);
    }

    @Test 
    void testMovement() {
        Vector2D playerPos = game.getPlayer().getPosition();
        ArrayList<Vector2D> enemyPos = new ArrayList<Vector2D>(game.getEnemies().size());
        for (int j = 0; j < game.getEnemies().size(); j++) {
            enemyPos.add(new Vector2D(
                game.getEnemies().get(j).getPosition().getX(),
                game.getEnemies().get(j).getPosition().getY()
            ));
        }

        for (int i = 0; i < 999; i++) {
            if (game.endGame()) break;
            if (i > 0) {
                for (int j = 0; j < game.getEnemies().size(); j++) {
                    enemyPos.set(j, new Vector2D(
                        game.getEnemies().get(j).getPosition().getX(),
                        game.getEnemies().get(j).getPosition().getY()
                    ));
                }
            }

            if (isGround(playerPos.getX() + 1, playerPos.getY())) {
                game.movePlayerRight();
                playerPos.add(new Vector2D(1, 0));
                assertEquals(playerPos, game.getPlayer().getPosition());
            }

            if (game.endGame()) break;

            for (int j = 0; j < game.getEnemies().size(); j++) {
                 assertTrue(enemyPos.get(j) != game.getEnemies().get(j).getPosition());
                 enemyPos.set(j, game.getEnemies().get(j).getPosition());
            }

            game.freezeEnemies();
    
            if (isGround(playerPos.getX() - 1, playerPos.getY())) {
                game.movePlayerLeft();
                playerPos.add(new Vector2D(-1, 0));
                assertEquals(playerPos, game.getPlayer().getPosition());
            }

            if (game.endGame()) break;

            for (int j = 0; j < game.getEnemies().size(); j++) {
                assertTrue(enemyPos.get(j) == game.getEnemies().get(j).getPosition());   
            }

            game.unfreezeEnemies();
    
            if (isGround(playerPos.getX(), playerPos.getY() + 1)) {
                game.movePlayerDown();
                playerPos.add(new Vector2D(0, 1));
                assertEquals(playerPos, game.getPlayer().getPosition());
            }

            if (game.endGame()) break;
    
            if (isGround(playerPos.getX(), playerPos.getY() - 1)) {
                game.movePlayerUp();
                playerPos.add(new Vector2D(0, -1));
                assertEquals(playerPos, game.getPlayer().getPosition());
            }

            if (game.endGame()) break;
        }
    }

    boolean isGround(int x, int y) {
        return game.getMap().cells[x][y].tile instanceof Ground;
    }

}
