package CMPT276.Group12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CMPT276.Group12.Actions.UIAction;
import CMPT276.Group12.States.ApplicationState;

public class ApplicationTest {
    private Application app;

    @BeforeEach
    void setUp() {
        app = new Application();
    }

    @Test
    void testStateChanges() {
        assertEquals(app.getApplicationState(), ApplicationState.MENU);

        app.uiAction(UIAction.GAME);
        assertEquals(app.getApplicationState(), ApplicationState.GAME);

        app.uiAction(UIAction.PAUSE);
        assertEquals(app.getApplicationState(), ApplicationState.PAUSED);

        app.uiAction(UIAction.GAME);
        assertEquals(app.getApplicationState(), ApplicationState.GAME);

        app.uiAction(UIAction.MENU);
        assertEquals(app.getApplicationState(), ApplicationState.MENU);
    }
}
