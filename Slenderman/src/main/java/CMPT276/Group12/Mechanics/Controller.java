package CMPT276.Group12.Mechanics;
/**
 * @author Cordell Bonnieux
 * @version 0.1
 */


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import CMPT276.Group12.Application;
import CMPT276.Group12.Actions.*;

public class Controller implements KeyListener {
    private Application listener;

    /**
     * Init a new Controller with listener l
     * The Listener must have a valid inputAction(InputAction) method,
     * which is used to respond to human user input
     * @param l Game
     */
    public Controller(Application l) {
        listener = l;
    }

    @Override
    /**
     * Detects a KeyEvent and maps valid events to an InputAction, which is then
     * passed to the listener
     * Valid KeyEvents:
     *  w and up arrow
     *  d and right arrow
     *  s and down arrow
     *  a and left arrow
     *  space bar 
     * @param e KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        int input = e.getKeyCode();
        if (input == KeyEvent.VK_W || input == KeyEvent.VK_UP)
            listener.inputAction(InputAction.UP);
        else if  (input == KeyEvent.VK_D || input == KeyEvent.VK_RIGHT)
            listener.inputAction(InputAction.RIGHT);
        else if  (input == KeyEvent.VK_S || input == KeyEvent.VK_DOWN)
            listener.inputAction(InputAction.DOWN);
        else if  (input == KeyEvent.VK_A || input == KeyEvent.VK_LEFT)
            listener.inputAction(InputAction.LEFT);
        else if (input == KeyEvent.VK_SPACE)
            listener.inputAction(InputAction.SELECT);
        else if (input == KeyEvent.VK_ESCAPE)
            listener.inputAction(InputAction.ESCAPE);
    }

    /**
     * Receives a UIAction to be passed to Game
     * This method can be called by UI components when
     * the player selects a button
     * @param action UIAction
     */
    public void uiAction(UIAction action) {
        listener.uiAction(action);
    }

    /**
     * Unused event
     * @deprecated
     */
    @Override
    public void keyReleased(KeyEvent e) {
        return; // do nothing, unused
    }

    /**
     * Unused event
     * @deprecated
     */
    @Override
    public void keyTyped(KeyEvent e) {
        return; // do nothing, unused
    }
}