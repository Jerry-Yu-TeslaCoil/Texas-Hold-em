package table.mechanism.decision.impl;

import table.mechanism.decision.DecisionType;
import table.mechanism.decision.PlayerDecision;

/**
 * Call decision from the player.
 *
 * <p>
 *     When player decided to call the bet, instance this.
 *     The Stack value of the call shall be filled by the table according to the game rule.
 * </p>
 *
 * <p>
 *     This is considered thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class CallDecision implements PlayerDecision {

    @Override
    public DecisionType getDecisionType() {
        return DecisionType.CALL;
    }

}
