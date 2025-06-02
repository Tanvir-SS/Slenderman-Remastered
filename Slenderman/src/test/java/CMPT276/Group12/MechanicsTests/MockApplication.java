package CMPT276.Group12.MechanicsTests;

import CMPT276.Group12.Actions.InputAction;
import CMPT276.Group12.Actions.UIAction;
import CMPT276.Group12.Application;

public class MockApplication extends Application {
    private InputAction lastInputAction;
    private UIAction lastUIAction;

    @Override
    public void inputAction(InputAction action) {
        lastInputAction = action;
    }

    @Override
    public void uiAction(UIAction action) {
        lastUIAction = action;
    }

    public InputAction getLastInputAction() {
        return lastInputAction;
    }

    public UIAction getLastUIAction() {
        return lastUIAction;
    }
}