package CMPT276.Group12.UITests;
import static org.junit.jupiter.api.Assertions.*;

import CMPT276.Group12.UI.GameOverMenu;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Application;
import CMPT276.Group12.Actions.UIAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/*
 * GameOverMenuTest uses mock controller to see if buttons work properly
 * checks if image loads in and buttons are not null
 */
public class GameOverMenuTest {

    static class TestableGameOverMenu extends GameOverMenu {
        public TestableGameOverMenu(Controller controller, int score, int windowWidth, int windowHeight, float time) {
            super(controller, score, windowWidth, windowHeight, time);
        }

        public void callPaintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    private TestableGameOverMenu gameOverMenu;
    private TestController controller;

    static class TestController extends Controller {
        private static final List<UIAction> actionsGlobal = new ArrayList<>();
    
        public TestController() {
            super(new Application() {
                @Override
                public void uiAction(UIAction action) {
                    actionsGlobal.add(action);
                }
            });
        }
    
        public boolean wasActionCalled(UIAction action) {
            return actionsGlobal.contains(action);
        }
    
        public void clearActions() {
            actionsGlobal.clear();
        }
    }
    

    @BeforeEach
    public void setup() {
        controller = new TestController();
        gameOverMenu = new TestableGameOverMenu(controller, 42, 800, 600, 123.45f);
        gameOverMenu.setSize(800, 600);
        controller.clearActions();
    }

    @Test
    public void testReturnButtonTriggersMenuAction() {
        JButton returnButton = findButton(gameOverMenu, "Return to Main Menu");
        assertNotNull(returnButton);
        returnButton.doClick();
        assertTrue(controller.wasActionCalled(UIAction.MENU), "Return button should trigger MENU action");
    }

    @Test
    public void testPaintComponentDrawsBackground() throws Exception {
        var field = gameOverMenu.getClass().getSuperclass().getDeclaredField("backgroundImage");
        field.setAccessible(true);
        field.set(gameOverMenu, new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB));

        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        gameOverMenu.callPaintComponent(g2d); 
        g2d.dispose();

        boolean drawn = false;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if ((img.getRGB(x, y) >> 24) != 0x00) {
                    drawn = true;
                    break;
                }
            }
            if (drawn) break;
        }

        assertTrue(drawn, "paintComponent should draw the background image");
    }

    @Test
    public void testScoreAndTimeLabelsDisplayedCorrectly() {
        boolean scoreFound = containsLabelText(gameOverMenu, "Final Score: 42");
        boolean timeFound = containsLabelText(gameOverMenu, "Total Time: 123.45s");

        assertTrue(scoreFound, "Score label should display the correct score");
        assertTrue(timeFound, "Time label should display the correct time");
    }

    private JButton findButton(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton && text.equals(((JButton) c).getText())) {
                return (JButton) c;
            } else if (c instanceof Container) {
                JButton result = findButton((Container) c, text);
                if (result != null) return result;
            }
        }
        return null;
    }

    private boolean containsLabelText(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel && text.equals(((JLabel) c).getText())) {
                return true;
            } else if (c instanceof Container) {
                if (containsLabelText((Container) c, text)) return true;
            }
        }
        return false;
    }
}
