package table.mechanism.impl;

import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;

import java.math.BigDecimal;

public class CallDecision implements PlayerDecision {

    @Override
    public DecisionType getDecisionType() {
        return DecisionType.CALL;
    }

}
