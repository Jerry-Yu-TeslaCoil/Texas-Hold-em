package table.pot.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import table.mechanism.ResolvedAction;
import table.mechanism.DecisionType;
import table.player.CardPlayer;
import table.pot.PlayerRanking;
import table.pot.PotManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
@Log4j2
public class PotManagerImpl implements PotManager {
    private final HashMap<CardPlayer, BigDecimal> playerStack;

    /**
     * Construct a pot manager with the players in the game.
     */
    public PotManagerImpl() {
        this.playerStack = new HashMap<>();
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
                BigDecimal currentBet = playerStack.getOrDefault(cardPlayer, BigDecimal.ZERO);
                BigDecimal newBet = verifyAndResolveBet(cardPlayer, playerDecision, currentBet);
                playerStack.put(cardPlayer, newBet);
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
        LinkedList<PotStack> pots = new LinkedList<>();
        LinkedList<ArrayList<CardPlayer>> joinedPlayers = new LinkedList<>();
        processPots(pots, joinedPlayers);
        log.info(pots);
        log.info(joinedPlayers);
        int potsCount = pots.size();
        HashMap<CardPlayer, Integer> playerScore = new HashMap<>();
        while (!playerRankings.isEmpty()) {
            playerScore.put(playerRankings.peek().getPlayer(), playerRankings.peek().getScore());
            playerRankings.remove();
        }
        //Judge the winners of every pot
        for (int i = 0; i < potsCount; i++) {
            PotStack pot = pots.get(i);
            ArrayList<CardPlayer> players = joinedPlayers.get(i);
            for (int j = 0, playersSize = players.size(); j < playersSize; j++) {
                CardPlayer player = players.get(j);
                //int rank = rankedPlayers.indexOf(player);
                //TODO: Finish the judging part
            }
        }
    }

    private void processPots(LinkedList<PotStack> pots, LinkedList<ArrayList<CardPlayer>> joinedPlayers) {
        List<Map.Entry<CardPlayer, BigDecimal>> entries = new ArrayList<>(playerStack.entrySet());
        entries.sort(Map.Entry.comparingByValue());
        //For every joining player, ranking from low to high
        log.info(this.getPlayerStack());
        //For every player in the game, allocate side pots their stacks
        entries.forEach(entry -> {
            log.info("judging player {} with stack {}", entry.getKey(), entry.getValue());
            if (entry.getKey().getIsContinuingGame()) {
                log.info("player {} is continuing game", entry.getKey());
                BigDecimal potBet = entry.getValue();
                if (potBet.compareTo(BigDecimal.ZERO) > 0) {
                    PotStack pot = new PotStack();
                    ArrayList<CardPlayer> currentJoinedPlayers = new ArrayList<>();
                    //Every player put stacks in the pot
                    entries.forEach(betEntry -> {
                        if (betEntry.getValue().compareTo(potBet) >= 0) {
                            betEntry.setValue(betEntry.getValue().subtract(potBet));
                            pot.addAmount(potBet);
                            currentJoinedPlayers.add(betEntry.getKey());
                        } else {
                            pot.addAmount(betEntry.getValue());
                            betEntry.setValue(BigDecimal.ZERO);
                        }
                    });
                    pots.add(pot);
                    joinedPlayers.add(currentJoinedPlayers);
                }
            }
        });
    }

    /**
     * Get a map representing each player's prize.
     * Do call this after judge().
     * @return A map of result stacks.
     */
    @Override
    public HashMap<CardPlayer, BigDecimal> getPlayerStack() {
        return this.playerStack;
    }

    /**
     * Clean the pot.
     */
    @Override
    public void clearStack() {
        this.playerStack.clear();
    }

    @Setter
    @Getter
    @ToString
    private static class PotStack {
        private BigDecimal amount = BigDecimal.ZERO;

        public void addAmount(BigDecimal amount) {
            this.amount = this.amount.add(amount);
        }
    }
}
