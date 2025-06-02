package CMPT276.Group12.GameObjects.Actors;

import java.util.Random;
import CMPT276.Group12.GameObjects.AnimatedGameObject;
import CMPT276.Group12.States.EnemyState;
import CMPT276.Group12.Utility.Vector2D;
import CMPT276.Group12.Mechanics.StateMachine;
import CMPT276.Group12.Mechanics.Cell;

/**
 * Represents an enemy character in the game.
 */
public class Enemy extends AnimatedGameObject<EnemyState> {
    private static String[] eSprites = {
            "/sprites/mono_set/slenderman/1.png",
            "/sprites/mono_set/slenderman/2.png",
            "/sprites/mono_set/slenderman/3.png",

    };
    private Cell[][] cells;
    private Random rand;
    private static final double LINE_OF_SIGHT_STEP = 0.5;

    private enum Direction {
        UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public Vector2D getOffset() {
            return new Vector2D(dx, dy);
        }
    }

    public Enemy(int spriteSize) {
        super(spriteSize, eSprites);
        stateMachine = new StateMachine<EnemyState>(EnemyState.ACTIVE);
        rand = new Random();
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    /*
     * if active, move in a random direction or closer if the player is within 4
     * cells and has line of sight
     */
    public void updateEnemyPosition(Player player) {
        if (super.stateMachine.getState() == EnemyState.ACTIVE && cells != null) {
            double distance = Math.sqrt(Math.pow(player.getPosition().getX() - position.getX(), 2) +
                    Math.pow(player.getPosition().getY() - position.getY(), 2));
            if (distance < 4 && hasLineOfSight(player)) {
                moveTowardsPlayer(player);
            } else {
                moveInRandomDirection();
            }
        }
    }

    public void moveInRandomDirection() {
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[rand.nextInt(directions.length)];
            Vector2D newPos = new Vector2D(position);
            newPos.add(direction.getOffset());

            if (isValidMove(newPos)) {
                position = newPos;
                break;
            }
        }
    }

    public void moveTowardsPlayer(Player player) {
        // Calculate direction vector
        double dx = player.getPosition().getX() - position.getX();
        double dy = player.getPosition().getY() - position.getY();

        // Normalize to get unit vector
        double length = Math.sqrt(dx * dx + dy * dy);
        dx /= length;
        dy /= length;

        // Round to nearest integer direction (-1, 0, or 1)
        Vector2D direction = new Vector2D(
                (int) Math.round(dx),
                (int) Math.round(dy));

        // Try to move in that direction
        Vector2D newPos = new Vector2D(position);
        newPos.add(direction);

        if (isValidMove(newPos)) {
            position = newPos;
        }
    }

    public boolean hasLineOfSight(Player player) {
        // Get positions
        Vector2D enemyPos = this.position;
        Vector2D playerPos = player.getPosition();

        // Calculate direction vector
        double dx = playerPos.getX() - enemyPos.getX();
        double dy = playerPos.getY() - enemyPos.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Normalize direction vector
        dx /= distance;
        dy /= distance;

        // Check points along the line
        Vector2D checkPos = new Vector2D(enemyPos);
        for (double i = 0; i < distance; i += LINE_OF_SIGHT_STEP) {
            checkPos.setX((int) Math.round(enemyPos.getX() + dx * i));
            checkPos.setY((int) Math.round(enemyPos.getY() + dy * i));

            // Round to nearest cell
            int cellX = (int) Math.round(checkPos.getX());
            int cellY = (int) Math.round(checkPos.getY());

            // Check if point is within bounds
            if (cellX < 0 || cellX >= cells.length || cellY < 0 || cellY >= cells[0].length) {
                return false;
            }

            // Check if there's a wall blocking line of sight
            if (cells[cellX][cellY].tile.isSolid()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a move to the new position is valid
     * 
     * @param newPos The position to check
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(Vector2D newPos) {
        // Check bounds
        if (newPos.getX() < 0 || newPos.getX() >= cells.length ||
                newPos.getY() < 0 || newPos.getY() >= cells[0].length) {
            return false;
        }

        // Check if the tile is walkable (not a wall)
        return !cells[newPos.getX()][newPos.getY()].tile.isSolid();
    }

    public void freeze() {
        super.stateMachine.setState(EnemyState.FROZEN);
    }

    public void unfreeze() {
        super.stateMachine.setState(EnemyState.ACTIVE);
    }

    public boolean isActive() {
        return super.stateMachine.getState() == EnemyState.ACTIVE;
    }
}
