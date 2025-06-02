package CMPT276.Group12.UITests;

import static org.junit.jupiter.api.Assertions.*;

import CMPT276.Group12.UI.PlayerHUD;
import CMPT276.Group12.Utility.Time;
import CMPT276.Group12.Mechanics.Controller;
import CMPT276.Group12.Application;
import CMPT276.Group12.Actions.UIAction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerHUDTest {

    private PlayerHUD hud;
    private TestController controller;
    private DummyTime timer;

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

    static class DummyTime extends Time {
        private JLabel label;

        @Override
        public void initializeTime() {
            
        }

        @Override
        public void startTrackingTime() {
           
        }

        @Override
        public void stopTime() {
            if (label != null) {
                label.setText("Time: 9.99s");
            }
        }

        @Override
        public void startTime() {
            
        }

        @Override
        public void setLabelToUpdate(JLabel label) {
            this.label = label;
        }

        public JLabel getLinkedLabel() {
            return label;
        }
    }

    @BeforeEach
    public void setup() {
        controller = new TestController();
        timer = new DummyTime();
        hud = new PlayerHUD(controller, timer);
    }

    @Test
    public void testSetPagesUpdatesLabel() {
        hud.setPages(3,3);
        JLabel label = findLabel(hud, "Keys: 3 / 3");
        assertNotNull(label, "Pages label should update correctly");
    }

    @Test
    public void testSetScoreUpdatesLabel() {
        hud.setScore(100);
        JLabel label = findLabel(hud, "Score: 100");
        assertNotNull(label, "Score label should update correctly");
    }

    @Test
    public void testPauseButtonTriggersPauseActionAndStopsTime() {
        JButton pauseButton = findButton(hud, "pause");
        assertNotNull(pauseButton, "Pause button should exist");
        pauseButton.doClick();
        assertTrue(controller.wasActionCalled(UIAction.PAUSE), "Pause action should be triggered");
        assertEquals("Time: 9.99s", timer.getLinkedLabel().getText(), "Timer label should update on pause");
    }

    private JLabel findLabel(Container container, String text) {
        for (Component c : container.getComponents()) {
            if (c instanceof JLabel && text.equals(((JLabel) c).getText())) {
                return (JLabel) c;
            } else if (c instanceof Container) {
                JLabel result = findLabel((Container) c, text);
                if (result != null) return result;
            }
        }
        return null;
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
}
