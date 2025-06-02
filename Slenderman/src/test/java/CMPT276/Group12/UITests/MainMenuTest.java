package CMPT276.Group12.UITests;
import static org.junit.jupiter.api.Assertions.*;

import CMPT276.Group12.Actions.UIAction;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.UI.MainMenu;
import CMPT276.Group12.Application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * MainMenuTest uses mock controller to see if buttons work properly
 * checks if image loads in and buttons are not null
 */
public class MainMenuTest {

    private TestController testController;
    private MainMenu mainMenu;

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
        testController = new TestController();
        testController.clearActions();
        mainMenu = new MainMenu(testController);
    }

    @Test
    public void testStartButtonTriggersGameAction() {
        JButton startButton = findButton("Start");
        assertNotNull(startButton, "Start button should exist");
        startButton.doClick();
        assertTrue(testController.wasActionCalled(UIAction.GAME), "Start should trigger GAME action");
    }

    @Test
    public void testExitButtonTriggersExitAction() {
        JButton exitButton = findButton("Exit");
        assertNotNull(exitButton, "Exit button should exist");
        exitButton.doClick();
        assertTrue(testController.wasActionCalled(UIAction.EXIT), "Exit should trigger EXIT action");
    }

    @Test
    public void testBackgroundImageExists() throws Exception {
        var field = mainMenu.getClass().getSuperclass().getDeclaredField("backgroundImage");
        field.setAccessible(true);
        Object image = field.get(mainMenu);
        assertNotNull(image, "Background image must be set");
    }

    private JButton findButton(String label) {
        return findButtonIn(mainMenu, label);
    }

    private JButton findButtonIn(Container container, String label) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton && label.equals(((JButton) comp).getText())) {
                return (JButton) comp;
            } else if (comp instanceof Container) {
                JButton btn = findButtonIn((Container) comp, label);
                if (btn != null) return btn;
            }
        }
        return null;
    }
}
