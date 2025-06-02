package CMPT276.Group12.UITests;
import static org.junit.jupiter.api.Assertions.*;


import CMPT276.Group12.UI.PauseMenu;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Application;
import CMPT276.Group12.Actions.UIAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * PauseMenuTest uses mock controller to see if buttons work properly
 * checks if image loads in and buttons are not null
 */
public class PauseMenuTest {

    private PauseMenu pauseMenu;
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
        controller.clearActions();
        pauseMenu = new PauseMenu(controller);
    }

    @Test
    public void testResumeButtonTriggersGameAction() {
        JButton resumeButton = findButton(pauseMenu, "Resume");
        assertNotNull(resumeButton);
        resumeButton.doClick();
        assertTrue(controller.wasActionCalled(UIAction.GAME), "Resume button should trigger GAME action");
    }

    @Test
    public void testExitButtonTriggersMenuAction() {
        JButton exitButton = findButton(pauseMenu, "Exit to Main Menu");
        assertNotNull(exitButton);
        exitButton.doClick();
        assertTrue(controller.wasActionCalled(UIAction.MENU), "Exit button should trigger MENU action");
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
