package CMPT276.Group12;
/**
 * @author Cordell Bonnieux, Tanvir Shergill, Jordan Ko, Sikij Karki
 * @version 0.1
 */

import javax.swing.JFrame;

/**
 * Slenderman Game
 */
public class Main {    
    private static final int WINDOW_HEIGHT = 768;
    private static final int WINDOW_WIDTH = 992;
    public static void main(String[] args) {
        Application game = new Application();
        game.setTitle("Slenderman");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        game.setResizable(false);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
        game.start();
    }
}
