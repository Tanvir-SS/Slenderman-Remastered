package CMPT276.Group12.GameObjects.Environment;
/**
 * A Walkable Tile, which the Player & Enemies can traverse
 * used to spawn Player & Enemies
 */
public class StartTile extends Ground {

    public StartTile(int spriteSize, int spriteFrame) {
        super(spriteSize, spriteFrame);
        collision = false;
    }

    @Override
    public String[] getSprites(){
        return null;
    }
}
