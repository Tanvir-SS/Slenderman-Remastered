package CMPT276.Group12.GameObjects;

import CMPT276.Group12.Mechanics.StateMachine;
import java.awt.Graphics2D;

public abstract class AnimatedGameObject<T extends Enum<T>> extends GameObject {
    protected StateMachine<T> stateMachine;
    protected int drawCounter = 0;
    protected int frameDelay = 333;

    
    public AnimatedGameObject(int spriteSize, String[] sprites) {
        super(spriteSize, sprites);
    }

    /**
     * Draws this object's sprites, sprites change every frameDelay calls
     * @param g2 graphics renderer
     * @param x panel pos int
     * @param y panel pos int
     */
    @Override
    public void draw(Graphics2D g2, int x, int y){
        g2.drawImage(frames[idx], x, y, null);
        if (drawCounter != 0 && drawCounter % frameDelay == 0) {
            if (idx == frames.length - 1)
                idx = 0;
            else
                idx++;
        }
        drawCounter++;
    }

}
