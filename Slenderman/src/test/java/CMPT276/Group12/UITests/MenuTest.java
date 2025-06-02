package CMPT276.Group12.UITests;

import static org.junit.jupiter.api.Assertions.*;

import CMPT276.Group12.UI.Menu;
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
 * MenuTest takes mock controller class, checks if image is drawn and updates controller
 */
public class MenuTest {

    private TestableMenu menu;
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

    static class TestableMenu extends Menu {
        private boolean firstPressed = false;
        private boolean secondPressed = false;

        public TestableMenu(Controller controller) {
            this.controller = controller;
            initializeUI("Test Menu", "First", "Second");
        }

        @Override
        public void firstButtonPressed() {
            firstPressed = true;
        }

        @Override
        public void secondButtonPressed() {
            secondPressed = true;
        }

        public boolean isFirstPressed() {
            return firstPressed;
        }

        public boolean isSecondPressed() {
            return secondPressed;
        }

        public void callPaintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    @BeforeEach
    public void setup() {
        controller = new TestController();
        menu = new TestableMenu(controller);
        menu.setSize(800, 600);
    }

    @Test
    public void testFirstButtonActionTriggersOverride() {
        JButton firstButton = findButton(menu, "First");
        assertNotNull(firstButton);
        firstButton.doClick();
        assertTrue(menu.isFirstPressed(), "firstButtonPressed() should be triggered");
    }

    @Test
    public void testSecondButtonActionTriggersOverride() {
        JButton secondButton = findButton(menu, "Second");
        assertNotNull(secondButton);
        secondButton.doClick();
        assertTrue(menu.isSecondPressed(), "secondButtonPressed() should be triggered");
    }

    @Test
    public void testPaintComponentDrawsBackgroundIfSet() throws Exception {
        var field = menu.getClass().getSuperclass().getDeclaredField("backgroundImage");
        field.setAccessible(true);
        field.set(menu, new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB));

        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        menu.callPaintComponent(g2d);
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

        assertTrue(drawn, "paintComponent should render the background image");
    }

    private JButton findButton(Container container, String label) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton && label.equals(((JButton) comp).getText())) {
                return (JButton) comp;
            } else if (comp instanceof Container) {
                JButton btn = findButton((Container) comp, label);
                if (btn != null) return btn;
            }
        }
        return null;
    }
}
