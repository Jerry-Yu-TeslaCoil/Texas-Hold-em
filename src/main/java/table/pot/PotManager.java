package table.pot;

import table.mechanism.ResolvedAction;
import table.player.CardPlayer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.PriorityQueue;

public interface PotManager {
    void action(CardPlayer cardPlayer, ResolvedAction playerDecision);
    void settle();
    void judge(PriorityQueue<PlayerRanking> playerRankings);
    HashMap<CardPlayer, BigDecimal> getPlayerStack();
    void clearStack();
}