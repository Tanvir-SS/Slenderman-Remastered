package CMPT276.Group12.Mechanics;

import java.awt.Graphics2D;
import java.util.Random;

import CMPT276.Group12.GameObjects.AnimatedGameObject;
import CMPT276.Group12.GameObjects.Actors.Player;
import CMPT276.Group12.GameObjects.Environment.ExitTile;
import CMPT276.Group12.GameObjects.Environment.Ground;
import CMPT276.Group12.GameObjects.Environment.StartTile;
import CMPT276.Group12.GameObjects.Environment.Wall;
import CMPT276.Group12.Utility.Vector2D;

public class Map {
    public final int SPRITE_SIZE = 32;

    // 2D Matrix containing current stage's tile data
    public Cell[][] cells;

    public Map(int rows, int cols) {
        cells = new Cell[rows][cols];
    }

    /**
     * Builds a randomly generated map based on the stage
     * todo refactor and extract methods from this
     * 
     * @param player Player to be placed into new map
     */
    public void buildMap(Player player) {
        cells = new Cell[cells.length][cells[0].length];
        generateWalledMap();
        setExitTile();
        setStartTile(player);
        generateBarriers();
    }

    /**
     * Create map with walls along perimeter
     */
    private void generateWalledMap() {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                // Adds cells along perimeter
                if (x == 0 || y == 0 || x == cells.length - 1 || y == cells[0].length - 1) {
                    cells[x][y] = new Cell(new Wall(SPRITE_SIZE, 0));
                } else {
                    cells[x][y] = new Cell(new Ground(SPRITE_SIZE, 0));
                }
            }
        }
    }

    /**
     * Randomizes the creation of walls around map
     */
    private void generateBarriers() {
        Random rand = new Random();
        int numBarriers = (cells.length * cells[0].length) / 6; // About 1/6 of the map is barriers

        for (int i = 0; i < numBarriers; i++) {
            int x, y;
            // Ensure generating wall tiles within range row[2,28] col[2,22]
            // requires one less inorder to check perimeter
            x = rand.nextInt(cells.length - 3) + 2;
            y = rand.nextInt(cells[0].length - 3) + 2;
            
            // Ensures walls are not placed on top of exit or start tiles
            boolean blockExit = isNeighboringTile(x, y, cell -> cell.tile instanceof ExitTile);
            boolean blockStart = isNeighboringTile(x, y, cell -> cell.tile instanceof StartTile) 
            || cells[x][y].tile instanceof StartTile;

            if (!blockExit && !blockStart)
                cells[x][y] = new Cell(new Wall(SPRITE_SIZE, 0));
        }
    }

    /**
     * Checks if any neighboring tiles satisfy the given condition.
     *
     * @param x The x-coordinate of the current tile.
     * @param y The y-coordinate of the current tile.
     * @param condition The condition to check for each neighboring tile.
     * @return true if any neighboring tile satisfies the condition, false otherwise.
     */
    private boolean isNeighboringTile(int x, int y, java.util.function.Predicate<Cell> condition) {
        // Check all four neighbors (up, down, left, right)
        return condition.test(cells[x + 1][y]) || condition.test(cells[x - 1][y])
                || condition.test(cells[x][y + 1]) || condition.test(cells[x][y - 1]);
    }

    /**
     * Randomize exit tile along perimeter of map
     * Side where exit tile ends up on is randomized, corrected to not spawn in
     * corners
     */
    private void setExitTile() {
        Random rand = new Random();
        int side = rand.nextInt(4); // 0=Top, 1=Bottom, 2=Left, 3=Right
        int x, y;

        if (side == 0) {
            // top row exit spawn interval: row[0] col[1,28]
            x = rand.nextInt(cells.length - 2) + 1;
            y = 0;
        } else if (side == 1) {
            // bottom row exit spawn interval: row[29] col[1,28]
            x = rand.nextInt(cells.length - 2) + 1;
            y = cells[0].length - 1;
        } else if (side == 2) {
            // left col exit spawn interval: row[1,28] col[0]
            x = 0;
            y = rand.nextInt(cells[0].length - 2) + 1;
        } else {
            // right col exit spawn interval: row[29] col[1,28]
            x = cells.length - 1;
            y = rand.nextInt(cells[0].length - 2) + 1;
        }
        cells[x][y] = new Cell(new ExitTile(SPRITE_SIZE, 0));
    }

    /**
     * Function to position pages to be relatively far from starting position
     * Thank u ChatGPT!
     * 
     * @param pos1 2D vector representing starting position of player
     * @param pos2 2D vector containing random int coordinates
     * @return a double to ensure greater than minDistance
     */
    private double getDistance(Vector2D pos1, Vector2D pos2) {
        int dx = pos1.getX() - pos2.getX();
        int dy = pos1.getY() - pos2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Randomized start tile must be adjacent to wall perimeter
     * Offset 1 tile up,down,right, or left of perimeter tile
     * Side where start tile ends up on is randomized
     * 0=top, 1=bottom, 2=left, 3=right
     * 
     * @param actor the AnimatedGameObject to place in the map
     */
    public <T extends Enum<T>> void _setStartTile(AnimatedGameObject<T> actor) {
        Random rand = new Random();
        int side = rand.nextInt(4);
        int x, y;

        // Ensures player does not spawn on top of a wall!
        // Did not need to check for wall tiles, not generated yet!
        if (side == 0) {
            // top row exit spawn: row[1] col[1,28]
            x = rand.nextInt(cells.length - 2) + 1;
            y = 1;
        } else if (side == 1) {
            // bottom row exit spawn: row[28] col[1,28]
            x = rand.nextInt(cells.length - 2) + 1;
            y = cells[0].length - 2;
        } else if (side == 2) {
            // left col exit spawn: row[1,28] col[1]
            x = 1;
            y = rand.nextInt(cells[0].length - 2) + 1;
        } else {
            // right col exit spawn: row[1,28] col[28]
            x = cells.length - 2;
            y = rand.nextInt(cells[0].length - 2) + 1;
        }

        cells[x][y] = new Cell(new StartTile(SPRITE_SIZE, 0));
        actor.setPosition(new Vector2D(x, y));
    }

    public <T extends Enum<T>> void setStartTile(AnimatedGameObject<T> actor) {
        Random rand = new Random();

        int x = rand.nextInt(cells.length - 1) + 1;
        int y = rand.nextInt(cells[0].length - 1) + 1;
        while (!(cells[x][y].tile instanceof Ground)) {
            x = rand.nextInt(cells.length - 1) + 1;
            y = rand.nextInt(cells[0].length - 1) + 1;
        }

        cells[x][y] = new Cell(new StartTile(SPRITE_SIZE, 0));
        actor.setPosition(new Vector2D(x, y));
    }

    public <T extends Enum<T>> void setStartTile(AnimatedGameObject<T> actor, Vector2D avoidPosition, int avoidRange) {
        Random rand = new Random();

        int x = rand.nextInt(cells.length - 1) + 1;
        int y = rand.nextInt(cells[0].length - 1) + 1;
        boolean avoidedPosition = Math.abs(x - avoidPosition.getX()) > avoidRange
                && Math.abs(y - avoidPosition.getY()) > avoidRange;
        while (!(cells[x][y].tile instanceof Ground && avoidedPosition)) {
            x = rand.nextInt(cells.length - 1) + 1;
            y = rand.nextInt(cells[0].length - 1) + 1;
            avoidedPosition = Math.abs(x - avoidPosition.getX()) > avoidRange
                    && Math.abs(y - avoidPosition.getY()) > avoidRange;
        }

        cells[x][y] = new Cell(new StartTile(SPRITE_SIZE, 0));
        actor.setPosition(new Vector2D(x, y));
    }

    /**
     * Draw map
     * 
     * @param g2 the graphics2D object
     */
    public void drawMap(Graphics2D g2, Player player) {
        for (int y = 0; y < cells[0].length; y++) {
            for (int x = 0; x < cells.length; x++) {
                if (cells[x][y] != null
                        && getDistance(player.getPosition(), new Vector2D(x, y)) <= player.getVisionRadius())
                    cells[x][y].tile.draw(g2, x * SPRITE_SIZE, y * SPRITE_SIZE);
            }
        }
    }

    /**
     * Finds random position for interactables around map
     * Ensures interactables are relatively far from player spawn point
     * 
     * @return position which is a Vector2D object containing the random coordinates
     *         for each item
     */
    public Vector2D getEmptyGroundTile(Vector2D avoidancePosition) {
        Random rand = new Random();
        int minDistance = cells.length / 3; // Ensures interactables spawn far from start
        boolean found = false;
        Vector2D position = null;

        while (!found) {
            int x = rand.nextInt(cells.length);
            int y = rand.nextInt(cells[0].length);

            if (cells[x][y] != null && cells[x][y].tile instanceof Ground && cells[x][y].getInteractable() == null
                    && getDistance(avoidancePosition, new Vector2D(x, y)) > minDistance) {
                position = new Vector2D(x, y);
                found = true;
            }
        }
        return position;
    }
}
