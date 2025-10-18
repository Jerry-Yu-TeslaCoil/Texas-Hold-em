package table.pot.impl;

import table.mechanism.ResolvedAction;
import table.player.CardPlayer;
import table.pot.PlayerRanking;
import table.pot.PotManager;
import table.pot.StatisticsPotManager;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * The implementation of StaticsPotManager.
 *
 * <p>
 *     This is the decoration class of PotManager.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class StatisticsPotManagerImpl implements StatisticsPotManager {

    private final PotManager potManager;

    private BigDecimal totalInvestment;

    public StatisticsPotManagerImpl(PotManager potManager) {
        this.potManager = potManager;
    }

    @Override
    public BigDecimal getTotalInvestment() {
        return this.totalInvestment;
    }

    @Override
    public void action(CardPlayer cardPlayer, ResolvedAction playerDecision) {
        potManager.action(cardPlayer, playerDecision);
        totalInvestment = totalInvestment.add(playerDecision.value());
    }

    @Override
    public void judge(PriorityQueue<PlayerRanking> playerRankings) {
        potManager.judge(playerRankings);
    }

    @Override
    public HashMap<CardPlayer, BigDecimal> getPlayerPrizeStack() {
        return potManager.getPlayerPrizeStack();
    }

    @Override
    public void clearStack() {
        potManager.clearStack();
    }
}
