package CMPT276.Group12.GameObjects.Interactables;

import CMPT276.Group12.GameObjects.StaticGameObject;
import CMPT276.Group12.GameObjects.Actors.Player;

public abstract class Interactable extends StaticGameObject{
    public String name;

    public abstract void interact(Player player);
    public String getName(){
        return this.name;
    }

    public Interactable(int spriteSize, String[] spritesArr){
        super(spriteSize,spritesArr);
    }
}
