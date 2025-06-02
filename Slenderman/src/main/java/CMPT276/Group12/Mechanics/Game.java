package CMPT276.Group12.Mechanics;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import javax.swing.Timer;

import CMPT276.Group12.GameObjects.Actors.*;
import CMPT276.Group12.GameObjects.Environment.ExitTile;
import CMPT276.Group12.GameObjects.Environment.Wall;
import CMPT276.Group12.GameObjects.Interactables.BearTrap;
import CMPT276.Group12.GameObjects.Interactables.EnemyFreeze;
import CMPT276.Group12.GameObjects.Interactables.Interactable;
import CMPT276.Group12.GameObjects.Interactables.Page;
import CMPT276.Group12.Utility.Vector2D;

public class Game extends JPanel {
    // cell & display size: Aspect Ratio of 5:4
    public final int CELL_ROWS = 30;

    public final int CELL_COLUMNS = 24;

    // current stage, i.e. level
    private int stage = 0;

    // game map
    private Map map;

    // player
    private Player player;

    // todo create and refactor into interactables manager class
    // hold main interactables, helps win or lose game
    private Interactable[] interactables;

    // holds bonus interactables, helps enhance player
    private Interactable[] bonusInteractables;

    // used for bonus rewards to reappear
    private Timer bonusTimer;

    // list of current enemies
    //todo refactor all this enemy logic into an EnemyController class
    private ArrayList<Enemy> enemies;

    // used for enemy movement when player is trapped
    private Timer enemyMoveTimer = new Timer(1000, e -> processEnemyActions());

    private Timer freezeEnemyTimer = new Timer(10000, e -> unfreezeEnemies());

    int numInteractables = 20;

    public int bonusScore = 0;


    public Game() {
        setVisible(true);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        map = new Map(CELL_ROWS, CELL_COLUMNS);
        setPreferredSize(new Dimension(CELL_ROWS * map.SPRITE_SIZE, CELL_COLUMNS * map.SPRITE_SIZE));
        interactables = new Interactable[numInteractables];
        bonusInteractables = new Interactable[numInteractables];
        build();
        startBonusInteractableSpawn();
    }

    /**
     * Builds the current Map for the given this.stage
     */
    public void build() {
        if (stage == 3)
            // temporarily end game at 4th stage to satisfy project requirements
            player.die();
        else{
            enemyMoveTimer.stop();
            if (freezeEnemyTimer.isRunning())
                unfreezeEnemies();
            stage++;
            bonusScore += 10 * stage;
            enemies = new ArrayList<Enemy>();
            player = new Player(map.SPRITE_SIZE, 8, 4);
            map.buildMap(player);
            setEnemies();
            setInteractable();
            enemyMoveTimer.start();
        }
    }

    /**
     * Moves Player by Vector2D move
     * destination tile must be of type Ground, Start or Exit
     * destination cannot be beyond bounds of map
     * 
     * @param move Vector2D, amount to move the player by
     */
    private void movePlayer(Vector2D move) {
        int x = move.getX() + player.getPosition().getX();
        int y = move.getY() + player.getPosition().getY();

        if (!checkForExit(x,y) && positionInMapBounds(x, y)) {

            // move to tile, then check for interactables
            // todo refactor, again
            if (!map.cells[x][y].tile.isSolid()) {

                // move the player character
                player.move(move);

                // check for items
                //todo refactor
                Interactable item = map.cells[x][y].getInteractable();
                if (item != null) {

                    // use item
                    item.interact(player);

                    // freeze enemy - todo refactor & consolidate
                    if (item instanceof EnemyFreeze) {
                        addBonus(10);
                        freezeEnemies();
                    }

                    if (Arrays.asList(interactables).contains(item))
                        removeInteractable(item, interactables, player.getPosition());

                    else if (Arrays.asList(bonusInteractables).contains(item))
                        removeInteractable(item, bonusInteractables, player.getPosition());
                }
            }
        }
    }

    /**
     * Returns true if the x,y cell position is in the map bounds, false otherwise
     * @param x cell position row
     * @param y cell position column
     * @return boolean; true if (x,y) is in map bounds, false otherwise
     */
    private boolean positionInMapBounds(int x, int y) {
        return x >= 0 && x < map.cells.length && y >= 0 && y < map.cells[0].length && map.cells[x][y] != null;
    }

    /**
     * Builds next stage if position matches exit position & player has correct number of pages
     * @param x integer representing cell position x
     * @param y integer representing cell position y
     * @return bool: true if exit & pages == stage, false otherwise
     */
    private boolean checkForExit(int x, int y) {
        if (player.getPages() == stage && map.cells[x][y].tile instanceof ExitTile) {
            build();
            return true;
        }
        return false;
    }

    /**
     * Starts bonus reward spawn
     */
    private void startBonusInteractableSpawn() {
        // ensures no multiple timers if map instantiated more than once
        // dont think this is every used, but just a safe guard...
        if (bonusTimer != null) {
            bonusTimer.stop();
        }

        bonusTimer = new Timer(7000, e -> setBonusInteractable());
        bonusTimer.start();
    }

    /**
     * Sets bonus rewards onto map
     * Rewards spawn every 7 seconds, stays active for 7 secs before disappearing
     */
    private void setBonusInteractable() {
        Vector2D itemPosition = map.getEmptyGroundTile(player.getPosition());

        Interactable bonus;
        Random rand = new Random();
        int bonusType = rand.nextInt(1); // temp hard set to one type of reward

        // add more rewards if needed
        switch (bonusType) {
            case 0:
                bonus = new EnemyFreeze(map.SPRITE_SIZE);
                break;
            // temp disable...
            // case 1:
            // bonus = new VisionIncrease(SPRITE_SIZE, 3);
            // break;
            default:
                return;
        }

        bonus.setPosition(itemPosition);
        for (int i = 0; i < bonusInteractables.length; i++) {
            if (bonusInteractables[i] == null) {
                bonusInteractables[i] = bonus;
                map.cells[bonus.getPosition().getX()][bonus.getPosition().getY()].setInteractable(bonus);

                new Timer(7000, e -> removeInteractable(bonus, bonusInteractables, itemPosition)).start();

                break;
            }
        }
    }

    /**
     * Finds item to be removed from interactable array
     * Removes item from screen
     * 
     * @param target          the item to be removed
     * @param interactableArr the array to remove interactables from
     * @param position        position of the item on the map
     */
    private void removeInteractable(Interactable target, Interactable[] interactableArr, Vector2D position) {
        for (int i = 0; i < interactableArr.length; i++) {
            if (interactableArr[i] == target) {
                interactableArr[i] = null;
                break;
            }
        }
        map.cells[position.getX()][position.getY()].removeInteractable();
    }

    private void setEnemies() {
        for (int i = 0; i < stage; i++) {
            enemies.add(new Enemy(map.SPRITE_SIZE));
            enemies.get(i).setCells(map.cells);
            map.setStartTile(enemies.get(i), player.getPosition(), player.getVisionRadius());
        }
    }

    private void processEnemyActions() {
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                enemy.updateEnemyPosition(player);
            }
        }

        // player collision with enemy ends the game
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getPosition().getX() == player.getPosition().getX()
                    && enemies.get(i).getPosition().getY() == player.getPosition().getY()) {
                System.out.print("player has been slaughtered");
                player.die();
                return;
            }
        }
        enemyMoveTimer.start();
    }

    /**
     * Randomaly selects x,y coord to place interactables
     * Interactables can only be placed on Ground tiles
     * Ensures spawning of interactable is away from starting position
     * FLAW: items may spawn close to each other (may need to optimize ltr)
     */
    private void setInteractable() {
        // define the number of each interactable to be placed
        // Ensure sum of all items match interactables[] length!
        HashMap<String, Integer> interactableCounts = new HashMap<>();
        interactableCounts.put("Page", stage);
        interactableCounts.put("BearTrap", stage*2);

        // index used instead of i incase vector
        int index = 0;
        for (String type : interactableCounts.keySet()) {
            int count = interactableCounts.get(type);

            for (int i = 0; i < count; i++) {
                Vector2D itemPosition = map.getEmptyGroundTile(player.getPosition());
                if (itemPosition == null)
                    continue; // Just in case no valid spot is found

                Interactable interactable;

                switch (type) {
                    case "Page":
                        interactable = new Page(map.SPRITE_SIZE);
                        break;
                    case "BearTrap":
                        interactable = new BearTrap(map.SPRITE_SIZE, this);
                        break;
                    default:
                        continue;
                }

                interactable.setPosition(itemPosition);
                interactables[index++] = interactable;
                map.cells[itemPosition.getX()][itemPosition.getY()].setInteractable(interactable);
            }
        }

        // add wall tiles
        // todo refactor, currently n time
        for (int x = 0; x < map.cells.length; x++) {
            for (int y = 0; y < map.cells[0].length; y++) {
                if (map.cells[x][y] == null)
                    map.cells[x][y] = new Cell(new Wall(map.SPRITE_SIZE, 0));
            }
        }
    }

    /**
     * Returns true if an enemy is in player's vision range
     * @return boolean
     */
    private boolean playerIsNearEnemy() {
        for (int i = 0; i < enemies.size(); i++) {
            Vector2D pos = enemies.get(i).getPosition();
            if (
                Math.abs(pos.getX() - player.getPosition().getX()) <= player.getVisionRadius()+1 &&
                Math.abs(pos.getY() - player.getPosition().getY()) <= player.getVisionRadius()+1
                )
                return true;
        }
        return false;
    }

    /**
     * Determines if the player has been killed or not
     * @return bool; true if player is dead, false otherwise
     */
    public boolean endGame() {
        return player.isDead();
    }

    /**
     * Method created to respawn a page after player steps on bear-trap
     */
    public void respawnPage() {
        Vector2D itemPosition = map.getEmptyGroundTile(player.getPosition());
        if (itemPosition == null)
            return;

        Interactable newPage = new Page(map.SPRITE_SIZE);
        newPage.setPosition(itemPosition);

        for (int i = 0; i < interactables.length; i++) {
            if (interactables[i] == null) {
                interactables[i] = newPage;
                break;
            }
        }
        map.cells[itemPosition.getX()][itemPosition.getY()].setInteractable(newPage);

    }

    /**
     * paints all game sprites, called externally
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //todo refactor and improve
        setBackgroundColor();

        // paint map
        map.drawMap(g2, player);

        //todo refactor below, checks should be in one func, perhaps in player

        // paint interactables
        paintInteractables(g2);

        //todo do not paint if not in range
        paintBonusInteractables(g2);

        //todo do not paint if not in range
        paintEnemies(g2);

        // paint Player
        paintPlayer(g2);

        // paint Enemies
        g2.dispose();
    }

    private void setBackgroundColor() {
        if (freezeEnemyTimer.isRunning()) {
            setBackground(new Color(12, 33, 69, 255));
        } else if (playerIsNearEnemy() || player.isTrapped()) {
            setBackground(new Color(77, 11, 17, 255));
        } else {
            setBackground(Color.BLACK);
        }
    }

    private void paintInteractables(Graphics2D g2){
        for (int i = 0; i < interactables.length; i++) {
            if (interactables[i] != null && 
            Math.abs(interactables[i].getPosition().getX()-player.getPosition().getX()) < player.getVisionRadius() &&
            Math.abs(interactables[i].getPosition().getY()-player.getPosition().getY()) < player.getVisionRadius() 
            ) {
                interactables[i].draw(g2, interactables[i].getPosition().getX() * map.SPRITE_SIZE,
                        interactables[i].getPosition().getY() * map.SPRITE_SIZE);
            }
        }
    }

    private void paintBonusInteractables(Graphics2D g2){
        for (int i = 0; i < bonusInteractables.length; i++) {
            if (bonusInteractables[i] != null  && 
            Math.abs(bonusInteractables[i].getPosition().getX()-player.getPosition().getX()) < player.getVisionRadius() &&
            Math.abs(bonusInteractables[i].getPosition().getY()-player.getPosition().getY()) < player.getVisionRadius()
            ) {
                bonusInteractables[i].draw(g2, bonusInteractables[i].getPosition().getX() * map.SPRITE_SIZE,
                        bonusInteractables[i].getPosition().getY() * map.SPRITE_SIZE);

            }
        }
    }

    private void paintEnemies(Graphics2D g2){        
        for (int i = 0; i < enemies.size(); i++) {
            if (
                Math.abs(enemies.get(i).getPosition().getX()-player.getPosition().getX()) < player.getVisionRadius() &&
                Math.abs(enemies.get(i).getPosition().getY()-player.getPosition().getY()) < player.getVisionRadius()
            ) {
                enemies.get(i).draw(g2, enemies.get(i).getPosition().getX() * map.SPRITE_SIZE, enemies.get(i).getPosition().getY() * map.SPRITE_SIZE);
            }
        } 
    }

    private void paintPlayer(Graphics2D g2) {
        player.draw(g2, player.getPosition().getX() * map.SPRITE_SIZE, player.getPosition().getY() * map.SPRITE_SIZE);
    }

    public int getScore() {
        return ((stage - 1) * 10) + bonusScore;
    }

    public void addBonus(int amt) {
        if (amt > 0)
            bonusScore += amt;
    }

    /**
     * Returns the current number of pages held by the player
     * used externally to update the PlayerHUD
     * 
     * @return int, number of pages the player has
     */
    public int getPages() {
        return player.getPages();
    }

    /**
     * Returns the current stage of this game instance
     * @return int stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * Move the player one cell up
     */
    public void movePlayerUp() {
        movePlayer(new Vector2D(0, -1));
    }

    /**
     * Move the player one cell down
     */
    public void movePlayerDown() {
        movePlayer(new Vector2D(0, 1));
    }

    /**
     * Move the player one cell to the left
     */
    public void movePlayerLeft() {
        movePlayer(new Vector2D(-1, 0));
    }

    /**
     * Move the player one cell to the right
     */
    public void movePlayerRight() {
        movePlayer(new Vector2D(1, 0));
    }

    /**
     * Starts automatic enemy movement
     */
    /*
    public void startEnemyMovement() {
        if (enemyMoveTimer == null) {
            enemyMoveTimer = new Timer(250, e -> {
                for (Enemy enemy : enemies) {
                    if (enemy.isActive()) {
                        enemy.updateEnemyPosition(player);
                    }
                }
            });
        }
        enemyMoveTimer.start();
    }
    */

    /**
     * Stops automatic enemy movement
     */
    /*
    public void stopEnemyMovement() {
        if (enemyMoveTimer != null) {
            enemyMoveTimer.stop();
        }
    }
    */

    /**
     * Freezes All enemies, starts timer to unfreeze when finished
     */
    public void freezeEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isActive())
                enemies.get(i).freeze();
        }
        freezeEnemyTimer.start();
    }

    /**
     * Unfreezes all enemies
     */
    public void unfreezeEnemies() {
        if (freezeEnemyTimer.isRunning())
            freezeEnemyTimer.stop();
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isActive())
                enemies.get(i).unfreeze();
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public Interactable[] getInteractables() {
        return interactables;
    }

    public Player getPlayer() {
        return player;
    }

    public Map getMap() {
        return map;
    }
}
