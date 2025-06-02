package CMPT276.Group12.UI;

import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Actions.UIAction;
import java.awt.*;



/*
 * PauseMenu Top panel shows controls WASD
 * Bottom Panel shows buttons
 */
public class PauseMenu extends Menu {

    public PauseMenu(Controller controller) {
        this.controller = controller;
        setPreferredSize(new Dimension(992, 768));//Newest change to keep size of menu same
        //this.backgroundImage = new ImageIcon(getClass().getResource("/UI/pausemenu.jpg")).getImage();
        initializeUI("Controls: WASD to move", "/UI/pausemenu_title.png","Resume","Exit to Main Menu");
    }

    @Override
    public void firstButtonPressed() {
            controller.uiAction(UIAction.GAME); // Resume the game
  
    }

    @Override
    public void secondButtonPressed() {
     
            controller.uiAction(UIAction.MENU); // Exit to main menu
       
    }
    
}
