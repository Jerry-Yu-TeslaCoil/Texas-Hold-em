package table.pot.impl;

import table.mechanism.ResolvedAction;
import table.mechanism.DecisionType;
import table.player.CardPlayer;
import table.player.PlayerList;
import table.pot.PlayerRanking;
import table.pot.PotManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * The normal pot manager.
 *
 * <p>
 *     This is used for most circumstances.
 * </p>
 *
 * <p>
 *     Not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class PotManagerImpl implements PotManager {
    private final ArrayList<PotStack> stacks;
    private final PlayerList players;
    private final HashMap<CardPlayer, BigDecimal> tempStack;

    /**
     * Construct a pot manager with the players in the game.
     * @param players The players of the round.
     */
    public PotManagerImpl(PlayerList players) {
        this.stacks = new ArrayList<>();
        this.stacks.add(new PotStack());
        this.tempStack = new HashMap<>();
        this.players = players;
    }

    /**
     * The impl sees the decision as accumulation of stacks in the sub pot, which means:
     *
     * <ul>
     *     <li>
     *         CALL, RAISE decision adds stacks to their sub pot.
     *     </li>
     *     <li>
     *         FOLD decision does nothing. Do call setIsContinuingGame(false) at Table.
     *     </li>
     * </ul>
     *
     * This is called every action.
     * And since it operates only on sub pots, do call judge() at every end of the game.
     *
     * @param cardPlayer The player who makes the decision.
     * @param playerDecision The reacted decision.
     */
    @Override
    public void action(CardPlayer cardPlayer, ResolvedAction playerDecision) {
        DecisionType decisionType = playerDecision.decisionType();
        switch (decisionType) {
            case CALL, RAISE -> {
                BigDecimal currentBet = tempStack.getOrDefault(cardPlayer, BigDecimal.ZERO);
                BigDecimal newBet = verifyAndResolveBet(cardPlayer, playerDecision, currentBet);
                tempStack.put(cardPlayer, newBet);
            }
            case FOLD -> {
                //DO NOTHING
            }
            default -> throw new RuntimeException("Unexpected decision type: " + decisionType);
        }
    }

    private static BigDecimal verifyAndResolveBet(CardPlayer cardPlayer, ResolvedAction playerDecision, BigDecimal currentBet) {
        BigDecimal value = playerDecision.value();
        if (value == null) {
            throw new NullPointerException("Bet cannot be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Bet must be greater than 0.");
        }
        if (value.compareTo(cardPlayer.getStack()) > 0) {
            throw new IllegalArgumentException("Bet must be no more than how much player process.");
        }
        return currentBet.add(value);
    }

    /**
     * Compute the result of every players' stack.
     * This method will allocate stacks by the accumulation of each player, their state and the poker result.
     *
     * <p>
     *     For example:<br>
     *     Player1(4): |###|#(ALLIN)<br>
     *     Player2(6): |###|#|##|<br>
     *     Player3(3): |###(ALLIN)<br>
     *     Player4(6): |###|#|##|<br>
     *     Player5(2): |##(FOLD)<br>
     *     Player6(5): |###|#|#(FOLD)<br>
     * </p>
     * <p>
     *     At this case, Player1, 2, 3, 4, 5, 6 joined the first pot, Player5 FOLD.
     *     This way comparison will happen between 1, 2, 3, 4, 6,
     *     with each of their 3 stacks and Player5's 2 stacks;<br>
     *     Player1, 2, 4, 6 joined the second pot,
     *     comparison will happen between Player1, 2, 4, 6,
     *     with each of their 1 stack;<br>
     *     Player2, 4, 6 joined the third pot, Player6 FOLD.
     *     Comparison will happen between Player2, 4,
     *     with each of their 2 stacks and Player6's 1 stack.<br>
     *     By ranking the players, for example, [1, 5, 2, 4, 3, 6] as their actual hole card value,
     *     Player1 will win the first pot and the second pot, Player2 will win the third pot.
     * </p>
     *
     * @param playerRankings The rank of the players.
     */
    @Override
    public void judge(PriorityQueue<PlayerRanking> playerRankings) {

    }

    /**
     * Get a map representing each player's prize.
     * Do call this after judge().
     * @return A map of result stacks.
     */
    @Override
    public HashMap<CardPlayer, BigDecimal> getPlayerStack() {
        return null;
    }

    /**
     * Clean the pot.
     */
    @Override
    public void clearStack() {
        this.stacks.clear();
        this.stacks.add(new PotStack());
        this.tempStack.clear();
    }

    private static class PotStack {
        private BigDecimal amount;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public void addAmount(BigDecimal amount) {
            this.amount = this.amount.add(amount);
        }
    }
}
