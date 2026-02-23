package table.pot.impl;

import exception.IllegalOperationException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import table.player.CardPlayer;
import table.pot.PlayerRanking;
import table.pot.PotManager;
import table.rule.decision.DecisionType;
import table.rule.decision.ResolvedAction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * The normal pot manager.
 *
 * <p>
 * This is used for most circumstances.
 * </p>
 *
 * <p>
 * Not thread-safe.
 * </p>
 *
 * @author jerry
 * @version 1.0
 */
@Log4j2
public class PotManagerImpl implements PotManager {
    private final HashMap<CardPlayer, BigDecimal> playerStack;
    private final HashMap<CardPlayer, BigDecimal> playerPrizeStack;

    private boolean isJudged;
    private BigDecimal totalInvestment;

    /**
     * Construct a pot manager with the players in the game.
     */
    public PotManagerImpl() {
        this.playerStack = new HashMap<>();
        playerPrizeStack = new HashMap<>();
        isJudged = false;
        totalInvestment = BigDecimal.ZERO;
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
     * <p>
     * This is called every action.
     * And since it operates only on sub pots, do call judge() at every end of the game.
     *
     * @param cardPlayer     The player who makes the decision.
     * @param playerDecision The reacted decision.
     */
    @Override
    public void action(CardPlayer cardPlayer, ResolvedAction playerDecision) {
        DecisionType decisionType = playerDecision.decisionType();
        switch (decisionType) {
            case CALL, RAISE -> {
                BigDecimal currentBet = playerStack.getOrDefault(cardPlayer, BigDecimal.ZERO);
                BigDecimal newBet = verifyAndResolveBet(playerDecision, currentBet);
                playerStack.put(cardPlayer, newBet);

                totalInvestment = totalInvestment.add(playerDecision.value());
            }
            case FOLD -> {
                //DO NOTHING
            }
            default -> throw new IllegalOperationException("Unexpected decision type: " + decisionType);
        }
    }

    private static BigDecimal verifyAndResolveBet(ResolvedAction playerDecision,
                                                  BigDecimal currentBet) {
        BigDecimal value = playerDecision.value();
        if (value == null) {
            throw new NullPointerException("Bet cannot be null.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Bet must be greater than 0. The current bet is " + value);
        }
        return currentBet.add(value);
    }

    /**
     * Compute the result of every players' stack.
     * This method will allocate stacks by the accumulation of each player, their state and the poker result.
     *
     * <p>
     * For example:<br>
     * Player1(4): |###|#(ALLIN)<br>
     * Player2(6): |###|#|##|<br>
     * Player3(3): |###(ALLIN)<br>
     * Player4(6): |###|#|##|<br>
     * Player5(2): |##(FOLD)<br>
     * Player6(5): |###|#|#(FOLD)<br>
     * </p>
     * <p>
     * At this case, Player1, 2, 3, 4, 5, 6 joined the first pot, Player5 FOLD.
     * This way comparison will happen between 1, 2, 3, 4, 6,
     * with each of their 3 stacks and Player5's 2 stacks;<br>
     * Player1, 2, 4, 6 joined the second pot,
     * comparison will happen between Player1, 2, 4, 6,
     * with each of their 1 stack;<br>
     * Player2, 4, 6 joined the third pot, Player6 FOLD.
     * Comparison will happen between Player2, 4,
     * with each of their 2 stacks and Player6's 1 stack.<br>
     * By ranking the players, for example, [1, 5, 2, 4, 3, 6] as their actual hole card value,
     * Player1 will win the first pot and the second pot, Player2 will win the third pot.
     * </p>
     *
     * <p>
     * BE AWARE: After judge(), all stacks in buffer will be clear. This is obvious and reasonable as
     * every player puts stacks in the pots, and this means DO NOT call judge() twice.
     * </p>
     *
     * @param playerRankings The rank of the players.
     */
    @Override
    public void judge(PriorityQueue<PlayerRanking> playerRankings) {
        if (isJudged) {
            log.warn("judge() is already called. This will lead to empty player stacks, and will not be functional.");
            return;
        }
        LinkedList<PotStack> pots = new LinkedList<>();
        LinkedList<ArrayList<CardPlayer>> joinedPlayers = new LinkedList<>();

        if (onlyOneWinner(playerRankings)) return;
        PriorityQueue<PlayerRanking> tempQueue;

        processPots(pots, joinedPlayers);
        log.trace(pots);
        log.trace(getSimpleStringList(joinedPlayers));

        tempQueue = new PriorityQueue<>(playerRankings);
        judgePotWinnerPrizes(tempQueue, pots, joinedPlayers);
        isJudged = true;
    }

    private boolean onlyOneWinner(PriorityQueue<PlayerRanking> playerRankings) {
        int isContinuing = 0;
        CardPlayer continuingPlayer = null;
        BigDecimal totalBet = BigDecimal.ZERO;
        ArrayList<CardPlayer> players = new ArrayList<>();
        PriorityQueue<PlayerRanking> tempQueue = new PriorityQueue<>();
        while (!playerRankings.isEmpty()) {
            PlayerRanking playerRanking = playerRankings.poll();
            players.add(playerRanking.getPlayer());
            tempQueue.add(playerRanking);
        }
        while (!tempQueue.isEmpty()) {
            PlayerRanking playerRanking = tempQueue.poll();
            playerRankings.add(playerRanking);
            totalBet = totalBet.add(this.playerStack.getOrDefault(playerRanking.getPlayer(), BigDecimal.ZERO));
        }
        for (CardPlayer player : players) {
            if (player.getIsContinuingGame()) {
                isContinuing++;
                continuingPlayer = player;
            }
        }
        if (isContinuing == 1) {
            this.playerPrizeStack.put(continuingPlayer, totalBet);
            return true;
        }
        return false;
    }

    private static List<List<String>> getSimpleStringList(LinkedList<ArrayList<CardPlayer>> joinedPlayers) {
        return joinedPlayers
                .stream()
                .map((joinedPlayer) ->
                        joinedPlayer.stream()
                                .map(CardPlayer::toSimpleLogString)
                                .toList())
                .toList();
    }

    private void judgePotWinnerPrizes(PriorityQueue<PlayerRanking> playerRankings, LinkedList<PotStack> pots,
                                      LinkedList<ArrayList<CardPlayer>> joinedPlayers) {
        int potsCount = pots.size();
        HashMap<CardPlayer, Integer> playerScore = new HashMap<>();
        while (!playerRankings.isEmpty()) {
            playerScore.put(playerRankings.peek().getPlayer(), playerRankings.peek().getScore());
            playerRankings.remove();
        }
        for (int i = 0; i < potsCount; i++) {
            PotStack pot = pots.get(i);
            ArrayList<CardPlayer> players = joinedPlayers.get(i);
            if (players.isEmpty()) {
                throw new IllegalStateException("No players found.");
            }
            players.sort(Comparator.comparingInt(player -> -playerScore.getOrDefault(player, -1)));
            log.trace("Current judging pot: {}", pot);
            log.trace("Sorted joining players for the pot: {}",
                    players.stream().map(CardPlayer::toSimpleLogString).toList());
            for (CardPlayer player : players) {
                log.trace("Player score: {}", playerScore.get(player));
            }
            if (players.size() > 1) {
                for (CardPlayer player : players) {
                    player.setIsJoiningPot(true);
                }
            }
            allocateStackPrizes(players, playerScore, pot);
        }
    }

    private void allocateStackPrizes(ArrayList<CardPlayer> players, HashMap<CardPlayer,
            Integer> playerScore, PotStack pot) {
        if (players.size() > 1) {
            int highestScore = playerScore.getOrDefault(players.get(0), 0);
            int winnerPointer;
            for (winnerPointer = 1; winnerPointer < players.size(); winnerPointer++) {
                if (playerScore.getOrDefault(players.get(winnerPointer), 0) < highestScore) {
                    break;
                }
            }
            BigDecimal eachWinnerPrice = pot.getAmount().divide(
                    BigDecimal.valueOf(winnerPointer), 2, RoundingMode.FLOOR);
            for (int j = 0; j < winnerPointer; j++) {
                log.trace("Player 2 {} wins {}", players.get(j), eachWinnerPrice);
                playerPrizeStack.put(
                        players.get(j),
                        playerPrizeStack.getOrDefault(
                                players.get(j),
                                BigDecimal.ZERO
                        ).add(eachWinnerPrice)
                );
            }
        } else {
            log.trace("Player {} wins {}", players.get(0), pot.getAmount());
            this.playerPrizeStack.put(players.get(0), playerPrizeStack.getOrDefault(players.get(0), BigDecimal.ZERO)
                    .add(pot.getAmount()));
        }
    }

    private void processPots(LinkedList<PotStack> pots, LinkedList<ArrayList<CardPlayer>> joinedPlayers) {
        List<Map.Entry<CardPlayer, BigDecimal>> activePlayers = playerStack.entrySet().stream()
                .filter(entry -> entry.getKey().getIsContinuingGame())
                .sorted(Map.Entry.comparingByValue())
                .toList();

        log.trace("Pot Active Players: {}", activePlayers);

        List<Map.Entry<CardPlayer, BigDecimal>> allPlayers = playerStack.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        log.trace("All Players: {}", allPlayers);

        validateActivePlayers(activePlayers);

        for (Map.Entry<CardPlayer, BigDecimal> activePlayer : activePlayers) {
            BigDecimal currentBet = activePlayer.getValue();

            if (currentBet.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            PotStack sidePot = new PotStack();
            List<CardPlayer> eligiblePlayers = new ArrayList<>();

            processBetLevel(allPlayers, currentBet, sidePot, eligiblePlayers);

            if (sidePot.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                pots.add(sidePot);
                joinedPlayers.add(new ArrayList<>(eligiblePlayers));
            }
        }
        filterLastSidePotFoldPlayers(joinedPlayers);
    }

    private static void filterLastSidePotFoldPlayers(LinkedList<ArrayList<CardPlayer>> joinedPlayers) {
        ArrayList<CardPlayer> cardPlayers = joinedPlayers.get(joinedPlayers.size() - 1);
        joinedPlayers.remove(joinedPlayers.size() - 1);
        List<CardPlayer> list = cardPlayers.stream().filter(CardPlayer::getIsContinuingGame).toList();
        ArrayList<CardPlayer> lastActivePlayers = new ArrayList<>(list);
        joinedPlayers.add(lastActivePlayers);
    }

    private static void validateActivePlayers(List<Map.Entry<CardPlayer, BigDecimal>> activePlayers) {
        BigDecimal highestBet = activePlayers.get(activePlayers.size() - 1).getValue();
        for (Map.Entry<CardPlayer, BigDecimal> activePlayer : activePlayers) {
            CardPlayer player = activePlayer.getKey();
            BigDecimal currentBet = activePlayer.getValue();
            if (currentBet.compareTo(highestBet) < 0 && !player.getIsAllIn()) {
                throw new IllegalOperationException("Exists non-allin player " + player.toSimpleLogString() +
                        " opening a new side pot. " +
                        "This might mean the player exceptionally didn't call but still continuing the game.");
            }
        }
    }

    private static void processBetLevel(List<Map.Entry<CardPlayer, BigDecimal>> allPlayers, BigDecimal currentBet,
                                        PotStack sidePot, List<CardPlayer> eligiblePlayers) {
        for (Map.Entry<CardPlayer, BigDecimal> player : allPlayers) {
            BigDecimal playerBet = player.getValue();
            if (playerBet.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal contribution = playerBet.min(currentBet);
                sidePot.addAmount(contribution);
                player.setValue(playerBet.subtract(contribution));

                if (playerBet.compareTo(currentBet) >= 0) {
                    eligiblePlayers.add(player.getKey());
                }
            }
        }
    }

    /**
     * Get a map representing each player's prize.
     * Do call this after judge().
     *
     * @return A map of result stacks.
     */
    @Override
    public HashMap<CardPlayer, BigDecimal> getPlayerPrizeStack() {
        return this.playerPrizeStack;
    }

    /**
     * Clean the pot.
     */
    @Override
    public void clearStack() {
        this.playerStack.clear();
        this.playerPrizeStack.clear();
        this.isJudged = false;
        this.totalInvestment = BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalInvestment() {
        return totalInvestment;
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
