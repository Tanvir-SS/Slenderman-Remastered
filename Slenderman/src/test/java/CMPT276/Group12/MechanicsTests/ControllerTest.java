package CMPT276.Group12.MechanicsTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Container;
import java.awt.event.KeyEvent;

import CMPT276.Group12.Actions.InputAction;
import CMPT276.Group12.Actions.UIAction;
import CMPT276.Group12.Mechanics.Controller;

public class ControllerTest {
    private Controller controller;
    private MockApplication mockApp;
    private Container container;

    @BeforeEach
    void setUp() {
        mockApp = new MockApplication();
        controller = new Controller(mockApp);
        container = new Container();
    }

    @Test
    void testUpMovementKeys() {
        // Test W key
        KeyEvent wKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_W, 'w');
        controller.keyPressed(wKey);
        assertEquals(InputAction.UP, mockApp.getLastInputAction());

        // Test Up Arrow key
        KeyEvent upKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_UP, '↑');
        controller.keyPressed(upKey);
        assertEquals(InputAction.UP, mockApp.getLastInputAction());
    }

    @Test
    void testDownMovementKeys() {
        // Test S key
        KeyEvent sKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_S, 's');
        controller.keyPressed(sKey);
        assertEquals(InputAction.DOWN, mockApp.getLastInputAction());

        // Test Down Arrow key
        KeyEvent downKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_DOWN, '↓');
        controller.keyPressed(downKey);
        assertEquals(InputAction.DOWN, mockApp.getLastInputAction());
    }

    @Test
    void testLeftMovementKeys() {
        // Test A key
        KeyEvent aKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_A, 'a');
        controller.keyPressed(aKey);
        assertEquals(InputAction.LEFT, mockApp.getLastInputAction());

        // Test Left Arrow key
        KeyEvent leftKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_LEFT, '←');
        controller.keyPressed(leftKey);
        assertEquals(InputAction.LEFT, mockApp.getLastInputAction());
    }

    @Test
    void testRightMovementKeys() {
        // Test D key
        KeyEvent dKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_D, 'd');
        controller.keyPressed(dKey);
        assertEquals(InputAction.RIGHT, mockApp.getLastInputAction());

        // Test Right Arrow key
        KeyEvent rightKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_RIGHT, '→');
        controller.keyPressed(rightKey);
        assertEquals(InputAction.RIGHT, mockApp.getLastInputAction());
    }

    @Test
    void testSpecialKeys() {
        // Test Space key
        KeyEvent spaceKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_SPACE, ' ');
        controller.keyPressed(spaceKey);
        assertEquals(InputAction.SELECT, mockApp.getLastInputAction());

        // Test Escape key
        KeyEvent escapeKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_ESCAPE, '⎋');
        controller.keyPressed(escapeKey);
        assertEquals(InputAction.ESCAPE, mockApp.getLastInputAction());
    }

    @Test
    void testUnusedKeys() {
        // Test an unused key (e.g., 'X')
        KeyEvent xKey = new KeyEvent(container, 0, 0, 0, KeyEvent.VK_X, 'x');
        controller.keyPressed(xKey);
        assertNull(mockApp.getLastInputAction());
    }

    @Test
    void testUIAction() {
        // Test passing a UI action
        UIAction testAction = UIAction.MENU;
        controller.uiAction(testAction);
        assertEquals(testAction, mockApp.getLastUIAction());
    }

    @Test
    void testUnusedKeyEvents() {
        new KeyEvent(container, 0, 0, 0, KeyEvent.VK_W, 'w');
        assertNull(mockApp.getLastInputAction());

        new KeyEvent(container, 0, 0, 0, KeyEvent.VK_W, 'w');
        assertNull(mockApp.getLastInputAction());
    }
}
