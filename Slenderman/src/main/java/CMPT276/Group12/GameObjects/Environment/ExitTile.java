package CMPT276.Group12.GameObjects.Environment;

/**
 * A Traverseable Tile which represents the Map exit
 */
public class ExitTile extends Tile {
    private static String[] exitSprites = {"/tiles/mono_set/exit.png"};
    
    public ExitTile(int spriteSize, int spriteFrame) {
        super(spriteSize, exitSprites, spriteFrame);
        collision = false;
    }

    @Override
    public String[] getSprites(){
        return exitSprites;
    }
}
