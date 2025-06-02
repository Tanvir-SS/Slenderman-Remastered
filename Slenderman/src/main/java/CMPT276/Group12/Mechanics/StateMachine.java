package CMPT276.Group12.Mechanics;

public class StateMachine<T extends Enum<T>> {
    private T currentState;

    /**
     * Constructs a new StateMachine with the specified initial state.
     *
     * @param initialState the initial state of the state machine
     */
    public StateMachine(T initialState) {
        this.currentState = initialState;
    }

    /**
     * Sets the current state of the state machine to the specified state.
     *
     * @param state the new state to set as the current state
     */
    public void setState(T state) {
        currentState = state;
    }

    /**
     * Retrieves the current state of the state machine.
     *
     * @return the current state of type T
     */
    public T getState() {
        return currentState;
    }

    @Override
    public String toString() {
        return "[Current State: " + currentState.toString()
                + " All States: " + currentState.getClass().getEnumConstants() + "]"

        ;
    }
}