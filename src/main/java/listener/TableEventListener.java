package listener;

/**
 * Listener for CardTable state change
 *
 * <p>
 *     The listener is used for events that needs to react when certain state occurs or state changes.
 *     For example, player controller listens to table to transmit context data to player UI or internet.
 * </p>
 *
 * <p>
 *     A list of this listeners will be maintained in CardTable, and when state changes, CardTable will inform
 *     every listener with state information and context data.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface TableEventListener {

    /**
     * Action function
     * TODO: Finish argument class
     */
    void execute();
}
