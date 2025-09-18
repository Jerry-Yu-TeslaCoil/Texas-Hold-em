package table.mechanism;

/**
 * Player decision interface.
 *
 * <p>
 *     This represents players' decision.
 *     With different sorts of PlayerDecision, operations would be different.
 *     But all decision shares some same methods.
 *     To use this, get decision type by getDecisionType() ahead, and cast the class to the corresponding class.
 * </p>
 *
 * <p>
 *     This is considered thread-safe.
 *     It is just an information carrier.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PlayerDecision {

    /**
     * Get the decision type of the player.
     * This is often used to know the subclass of the instance to cast it.
     * @return The type of the player's decision.
     */
    DecisionType getDecisionType();
}
