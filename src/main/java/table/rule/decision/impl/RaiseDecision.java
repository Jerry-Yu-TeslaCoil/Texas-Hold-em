package table.rule.decision.impl;

import table.rule.decision.DecisionType;
import table.rule.decision.PlayerDecision;

import java.math.BigDecimal;

/**
 * The raise decision of the player.
 *
 * <p>
 *     This is used when the player decides to raise bet.
 *     The client side should hand on the raised value.
 * </p>
 *
 * @param bet Raised bet, not including the calling part.
 */
public record RaiseDecision(BigDecimal bet) implements PlayerDecision {

    @Override
    public DecisionType getDecisionType() {
        return DecisionType.RAISE;
    }

}
