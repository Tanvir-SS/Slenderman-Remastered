package CMPT276.Group12.GameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;


import CMPT276.Group12.Utility.Vector2D;

public abstract class GameObject {
    private String[] sprites;
    protected int idx = 0;
    protected int SIZE;
    protected BufferedImage[] frames;
    protected Vector2D position = null;
    
    /**
     *  Creates new abstract GameObject
     * @param spriteSize int size dimension of sprite (supports square sprites only)
     * @param numOfFrames int number of frames in the sprite sheet
     * @param spriteSheetPath String path to sprite sheet
     */
    public GameObject(int spriteSize, String[] sprites) {
        frames = new BufferedImage[sprites.length];
        this.sprites = sprites;
        SIZE = spriteSize;
        setSpriteFrames();
    }

    /**
     * Loads the sprites into memory
     */
    private void setSpriteFrames() {
        try {
            for (int i = 0; i < frames.length; i++) {
                var resourceStream = getClass().getResourceAsStream(sprites[i]);
                if (resourceStream == null) {
                    System.err.println("Resource not found: " + sprites[i]);
                    frames[i] = null; // Set the frame to null if the resource is missing
                    continue;
                }
                frames[i] = ImageIO.read(resourceStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the current sprite frame
     * @param f int representing sprite frame to use
     */
    protected void setSpriteFrame(int f) {
        if (f < frames.length && f >= 0)
            idx = f;
    }

    /**
     * Sets the object's cell position
     * @param position Vector2D cell position of this object
     */
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    /**
     * Returns this object's cell position
     * @return cell position of this object
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Draws this object, and itterates over frames, this will create a 1D animation for
     * multi-spite instantiations, can be overwritten and modified to reduce frame rate
     * @param g2 Graphics2D
     * @param x 
     * @param y 
     */
    public void draw(Graphics2D g2, int x, int y){
        g2.drawImage(frames[idx], x, y, null);
    }
}
