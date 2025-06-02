package CMPT276.Group12.GameObjects.Environment;

import CMPT276.Group12.GameObjects.StaticGameObject;

public abstract class Tile extends StaticGameObject {
    protected boolean collision;

    public Tile(int spriteSize, String[] spritesArr, int spriteFrame) {
        super(spriteSize, spritesArr);
        setSpriteFrame(spriteFrame);
    }

    public abstract String[] getSprites();

    public boolean isSolid(){
        return this.collision;
    }
}
