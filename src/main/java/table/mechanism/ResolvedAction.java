package table.mechanism;

import java.math.BigDecimal;

public record ResolvedAction(DecisionType decisionType, BigDecimal value) {
}
