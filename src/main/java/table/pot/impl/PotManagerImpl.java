package table.pot.impl;

import table.mechanism.ResolvedAction;
import table.mechanism.DecisionType;
import table.player.CardPlayer;
import table.player.impl.PlayerCoil;
import table.pot.PlayerRanking;
import table.pot.PotManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class PotManagerImpl implements PotManager {
    private final ArrayList<PotStack> stacks;
    private final PlayerCoil players;
    private final HashMap<CardPlayer, BigDecimal> tempStack;

    public PotManagerImpl(PlayerCoil players) {
        this.stacks = new ArrayList<>();
        this.stacks.add(new PotStack());
        this.tempStack = new HashMap<>();
        this.players = players;
    }

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
                BigDecimal currentBet = tempStack.getOrDefault(cardPlayer, BigDecimal.ZERO);
                tempStack.put(cardPlayer, BigDecimal.ZERO);
                stacks.get(stacks.size() - 1).addAmount(currentBet);
            }
            default -> {

            }
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

    @Override
    public void settle() {

    }

    @Override
    public void judge(PriorityQueue<PlayerRanking> playerRankings) {

    }

    @Override
    public HashMap<CardPlayer, BigDecimal> getPlayerStack() {
        return null;
    }

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
