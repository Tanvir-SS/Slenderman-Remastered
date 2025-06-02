package CMPT276.Group12.GameObjects.Interactables;

import CMPT276.Group12.GameObjects.Actors.Player;

public class EnemyFreeze extends Interactable{
    private static String[] potion32pxPath ={
        "/sprites/mono_set/ice.png" // needs to be created
    };
    
    public EnemyFreeze(int spriteSize){
        super(spriteSize,potion32pxPath);
        this.name = "enemyFreeze";
    }

    @Override
    public void interact(Player player) {
    }
}
