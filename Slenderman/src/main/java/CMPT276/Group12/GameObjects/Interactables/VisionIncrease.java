package CMPT276.Group12.GameObjects.Interactables;

import CMPT276.Group12.GameObjects.Actors.Player;

public class VisionIncrease extends Interactable{
    private int increaseAmount;
    private static String[] flashLight32pxPath ={
        "/tiles/32px/interactables/flashlight1.png" // needs to be created
    };

    public VisionIncrease(int spriteSize, int increaseAmount){
        super(spriteSize, flashLight32pxPath);
        this.increaseAmount = increaseAmount;
        this.name = "visionIncrease";
    }

    public int increaseVision() {
        return increaseAmount;
    }

    @Override
    public void interact(Player player){
        System.out.println("Vision Increased!");
    }
}
