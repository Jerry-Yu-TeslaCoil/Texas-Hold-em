package table.mechanism;

import java.math.BigDecimal;

/**
 * The request of every decision according to the rule.
 *
 * <p>
 *     Basically, this is only a context for client side to judge.
 *     That means this record is only an information class provided for client side,
 *     to ensure the player's operation is valid.
 * </p>
 *
 * <p>
 *     This is considered thread-safe.
 *     Validation happened on the client side, making it highly unsafe.
 *     By that means, DO NOT believe client side check.
 *     Do always validate the player's decision on the table side.
 * </p>
 *
 * @param leastStackRequest The least stack required.
 *
 * @author jerry
 *
 * @version 1.0
 */
public record DecisionRequest(BigDecimal leastStackRequest) {
}
