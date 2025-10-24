package table.mechanism.decision;

import java.math.BigDecimal;

/**
 * The processed decision info.
 *
 * <p>
 *     Decisions are validated, processed, and cast to this ResolvedAction with decision type and value.
 *     That means info in the class is considered completely validated and safe.
 *     Do validate info and make sure no unauthorized modification exists.
 *     Do use this only in safe circumstances like Table and CardPlayer.
 * </p>
 *
 * <p>
 *     The value is resolved for PotManager.
 *     That means PotManager will not process the value.
 *     For example, the value of the CALL decision should be the required value,
 *     while the value of the RAISE decision should include both required and the raised bet,
 *     as PotManager should not know anything about the game rules.
 *     It should only know how to collect bet value, manage pots, not how much the RAISE decision needs.
 * </p>
 *
 * @param decisionType Decision type of the player decision.
 * @param value Resolved value of the current decision.
 *              Different from RaiseDecision.bet(), this includes all invested chips.
 *              For example, ALL_IN means all the stack the player have,
 *              CALL means current calling stack,
 *              RAISE means the calling part plus the raising part.
 *
 * @author jerry
 *
 * @version 1.0
 */
public record ResolvedAction(DecisionType decisionType, BigDecimal value) {
}
