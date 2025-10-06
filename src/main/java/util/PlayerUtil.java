package util;

import exception.IllegalOperationException;
import table.player.CardPlayer;

import java.math.BigDecimal;

/**
 * Player util class.
 *
 * <p>
 *     This class is for proceeding various actions about a player.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class PlayerUtil {

    /**
     * Charge the amount of stacks from a player as much as given amount.
     * If the stacks the player have cannot cover the request, charge all the rest.
     * @param player The player who needs to be charged.
     * @param amount The charging amount.
     * @return The actual charging amount.
     */
    public static BigDecimal collectChipsAsMuch(CardPlayer player, BigDecimal amount) {
        if (player.getStack().compareTo(amount) < 0) {
            BigDecimal stack = player.getStack();
            player.setStack(BigDecimal.ZERO);
            return stack;
        } else {
            player.setStack(player.getStack().subtract(amount));
            return amount;
        }
    }

    /**
     * Charge the amount of stacks from a player as exact.
     * If player's stacks cannot cover the requirement, throw an exception.
     * @param player The player to be charged.
     * @param amount The charging amount.
     * @throws IllegalOperationException When the player cannot cover the requirement.
     */
    public static void collectChipsExactly(CardPlayer player, BigDecimal amount) throws IllegalOperationException {
        if (player.getStack().compareTo(amount) < 0) {
            throw new IllegalOperationException("Player's stack cannot cover the requirement. Player's stack: " +
                    player.getStack() + ", charging " + amount);
        } else {
            player.setStack(player.getStack().subtract(amount));
        }
    }
}
