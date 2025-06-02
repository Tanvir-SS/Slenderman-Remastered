package CMPT276.Group12.GameObjects.Environment;

public class Ground extends Tile{
    private static String[] groundSprites = {
        "/tiles/mono_set/ground1.png",
        "/tiles/mono_set/ground2.png",
        "/tiles/mono_set/ground3.png",
        "/tiles/mono_set/ground4.png",
        "/tiles/mono_set/ground5.png",
    };

    /**
     * Init a new Ground tile
     * @param spriteSize the pixel dimension of this Ground Tile
     * @param spriteFrame the particular sprite frame to use, (idx of groundSprites)
     */
    public Ground(int spriteSize, int spriteFrame) {
        super(spriteSize, groundSprites, spriteFrame);
        collision = false;
    }

    @Override
    public String[] getSprites(){
        return groundSprites;
    }
}
