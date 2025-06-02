package CMPT276.Group12.UI;
import javax.swing.*;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Actions.UIAction;
import java.awt.*;

/*
 * MainMenu acts as opening screen that gives user option to start the game
 * or exit
 */
public class MainMenu extends Menu {

    /*
     * MainMenu constructor
     * calls method: initializeUI() to setup top and bottom Jpanels with their buttons
     */
    public MainMenu(Controller controller) {
        this.controller = controller;
        setPreferredSize(new Dimension(992, 768));//Newest change to keep size of menu same
        this.backgroundImage = new ImageIcon(getClass().getResource("/UI/mainmenu.jpg")).getImage();
        initializeUI("Slenderman", "/UI/logo.png","Start","Exit");
    }


    @Override
    public void firstButtonPressed() {
        
            controller.uiAction(UIAction.GAME); // Starts the game
        
    }

    @Override
    public void secondButtonPressed() {
        
            controller.uiAction(UIAction.EXIT); // Exits the game
       
    }
}
