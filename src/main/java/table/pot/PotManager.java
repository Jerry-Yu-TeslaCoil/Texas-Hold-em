package table.pot;

import table.mechanism.decision.ResolvedAction;
import table.player.CardPlayer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * PotManager class representing a pot with managing function.
 *
 * <p>
 *     This is used for pot managing.
 *     Every round, insert decisions to the pot, and the pot will automatically arrange pot, side pot, winner stacks.
 *     Call action() every decision, and call settle() to settle the round at the end of every round.
 *     When the game comes to an end, give the ranking to pot manager by calling judge(), and getPlayerStack().
 * </p>
 *
 * <p>
 *     This is considered not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PotManager {

    /**
     * Call pot manager to react to every action.
     * @param cardPlayer The player who makes the decision.
     * @param playerDecision The reacted decision.
     */
    void action(CardPlayer cardPlayer, ResolvedAction playerDecision);

    /**
     * Judge the game result by player ranking.
     * @param playerRankings The rank of the players.
     */
    void judge(PriorityQueue<PlayerRanking> playerRankings);

    /**
     * Get how much each player wins this round.
     * @return A map of every player's bonus.
     */
    HashMap<CardPlayer, BigDecimal> getPlayerPrizeStack();

    /**
     * Clean and initialize the stack.
     */
    void clearStack();
}