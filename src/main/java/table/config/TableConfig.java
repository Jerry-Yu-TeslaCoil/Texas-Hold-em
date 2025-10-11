package table.config;

import java.math.BigDecimal;

/**
 * The record for table configs.
 *
 * <p>
 *     This is for storing configs of the table.
 *     It will affect game rounds of this table, like BB.
 * </p>
 *
 * <p>
 *     Config class with only data, considered thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public record TableConfig(BigDecimal initBet, BigDecimal halfMinimumBet, BigDecimal maximumBet, int maxPlayers) {

    /**
     * Create config with appointed args.
     * @param initBet Bets that every player have initially.
     * @param halfMinimumBet THE HALF of the least bet for players to invest.
     * @param maximumBet The max bet for a turn of the game.
     * @param maxPlayers The max number of players of the table.
     */
    public TableConfig(BigDecimal initBet, BigDecimal halfMinimumBet, BigDecimal maximumBet, int maxPlayers) {
        if (initBet.compareTo(halfMinimumBet) <= 0) {
            throw new IllegalArgumentException("initBet must be greater than halfMinimumBet");
        }
        this.initBet = initBet;
        if (halfMinimumBet.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Minimum bet must be greater than zero");
        }
        this.halfMinimumBet = halfMinimumBet;
        if (maximumBet.compareTo(halfMinimumBet) < 0) {
            throw new IllegalArgumentException("Maximum bet must be greater than minimum bet");
        }
        this.maximumBet = maximumBet;
        if (maxPlayers <= 1) {
            throw new IllegalArgumentException("Max players must be greater than 1");
        }
        if (maxPlayers > 22) {
            throw new IllegalArgumentException("Players must be less than 22");
        }
        this.maxPlayers = maxPlayers;
    }

    /**
     * Create config with infinite maximum bet and maximum players.
     * @param initBet Bets that every player have initially.
     * @param halfMinimumBet THE HALF of the least bet for players to invest.
     */
    public TableConfig(BigDecimal initBet, BigDecimal halfMinimumBet) {
        this(initBet, halfMinimumBet, new BigDecimal(Integer.MAX_VALUE), 22);
    }
}
