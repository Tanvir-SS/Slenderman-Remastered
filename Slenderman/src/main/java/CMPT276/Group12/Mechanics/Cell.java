package CMPT276.Group12.Mechanics;

import java.awt.Graphics2D;

import CMPT276.Group12.GameObjects.Environment.Tile;
import CMPT276.Group12.GameObjects.Interactables.Interactable;

public class Cell {
    public Tile tile;
    public Interactable interactable;

    public Cell(Tile tile){
        this.tile = tile;
    }

    public void draw(Graphics2D g2, int x, int y){
        tile.draw(g2, x, y);
    }

    public void setInteractable(Interactable i) {
        this.interactable = i;
    }

    public Interactable getInteractable() {
        return interactable;
    }

    public void removeInteractable() {
        this.interactable = null;
    }
}
