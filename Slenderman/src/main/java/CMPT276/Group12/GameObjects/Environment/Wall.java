package CMPT276.Group12.GameObjects.Environment;

public class Wall extends Tile {
    private static String[] wallSprites = {"/tiles/mono_set/wall.png"};

    /**
     * Init a new Wall tile
     * @param spriteSize the pixel dimension of this Wall Tile
     * @param spriteFrame the particular sprite frame to use, (idx of wallSprites)
     */
    public Wall(int spriteSize, int spriteFrame) {
        super(spriteSize, wallSprites, spriteFrame);
        collision = true;
    }

    @Override
    public String[] getSprites(){
        return wallSprites;
    }
}
