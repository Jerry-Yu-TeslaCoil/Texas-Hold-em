package table.mechanism.impl;

import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;

import java.math.BigDecimal;

public record RaiseDecision(BigDecimal bet) implements PlayerDecision {

    @Override
    public DecisionType getDecisionType() {
        return DecisionType.RAISE;
    }

}
