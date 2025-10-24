package table.mechanism.decision.impl;

import table.mechanism.decision.DecisionType;
import table.mechanism.decision.PlayerDecision;

public class FoldDecision implements PlayerDecision {
    @Override
    public DecisionType getDecisionType() {
        return DecisionType.FOLD;
    }
}
