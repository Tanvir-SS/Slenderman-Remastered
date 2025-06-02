package CMPT276.Group12.GameObjects.Interactables;

import CMPT276.Group12.GameObjects.Actors.Player;

public class Page extends Interactable{
    private static String[] page32pxPath ={
        "/sprites/mono_set/key.png"
    };

    public Page(int spriteSize){
        super(spriteSize, page32pxPath);
        this.name = "page";
        
    }

    @Override
    public void interact(Player player){
        player.addPage();
    }
}
