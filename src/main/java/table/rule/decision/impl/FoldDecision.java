package table.rule.decision.impl;

import table.rule.decision.DecisionType;
import table.rule.decision.PlayerDecision;

public class FoldDecision implements PlayerDecision {
    @Override
    public DecisionType getDecisionType() {
        return DecisionType.FOLD;
    }
}
