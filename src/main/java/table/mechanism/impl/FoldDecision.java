package table.mechanism.impl;

import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;

public class FoldDecision implements PlayerDecision {
    @Override
    public DecisionType getDecisionType() {
        return DecisionType.FOLD;
    }
}
