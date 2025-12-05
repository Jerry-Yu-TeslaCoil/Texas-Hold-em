package table.rule.decision.impl;

import table.rule.decision.DecisionType;
import table.rule.decision.PlayerDecision;

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
