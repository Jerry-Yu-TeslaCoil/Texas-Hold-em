import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.mechanism.DecisionType;
import table.mechanism.ResolvedAction;
import table.player.CardPlayer;
import table.player.impl.SimpleCardPlayer;
import table.pot.PlayerRanking;
import table.pot.PotManager;
import table.pot.impl.PotManagerImpl;
import util.PlayerUtil;

import java.math.BigDecimal;
import java.util.PriorityQueue;

@Log4j2
public class TestPotManager {
    @Test
    public void testNormalProcess() {
        PotManager potManager = new PotManagerImpl();
        CardPlayer player1 = new SimpleCardPlayer(null, new BigDecimal(4), 1);
        CardPlayer player2 = new SimpleCardPlayer(null, new BigDecimal(7), 2);
        CardPlayer player3 = new SimpleCardPlayer(null, new BigDecimal(3), 3);
        CardPlayer player4 = new SimpleCardPlayer(null, new BigDecimal(6), 4);
        CardPlayer player5 = new SimpleCardPlayer(null, new BigDecimal(6), 5);
        CardPlayer player6 = new SimpleCardPlayer(null, new BigDecimal(6), 6);

        potManager.action(player1, new ResolvedAction(DecisionType.RAISE, new BigDecimal(1)));
        PlayerUtil.collectChipsExactly(player1, new BigDecimal(1));
        potManager.action(player2, new ResolvedAction(DecisionType.RAISE, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(2));
        potManager.action(player3, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player3, new BigDecimal(2));
        potManager.action(player4, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player4, new BigDecimal(2));
        potManager.action(player5, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player5, new BigDecimal(2));
        potManager.action(player6, new ResolvedAction(DecisionType.RAISE, new BigDecimal(5)));
        PlayerUtil.collectChipsExactly(player6, new BigDecimal(5));
        potManager.action(player1, new ResolvedAction(DecisionType.CALL, new BigDecimal(3)));
        PlayerUtil.collectChipsExactly(player1, new BigDecimal(3));
        potManager.action(player2, new ResolvedAction(DecisionType.CALL, new BigDecimal(3)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(3));
        potManager.action(player3, new ResolvedAction(DecisionType.CALL, new BigDecimal(1)));
        PlayerUtil.collectChipsExactly(player3, new BigDecimal(1));
        potManager.action(player4, new ResolvedAction(DecisionType.RAISE, new BigDecimal(4)));
        PlayerUtil.collectChipsExactly(player4, new BigDecimal(4));
        potManager.action(player5, new ResolvedAction(DecisionType.FOLD, new BigDecimal(0)));
        player5.setIsContinuingGame(false);
        potManager.action(player6, new ResolvedAction(DecisionType.FOLD, new BigDecimal(0)));
        player6.setIsContinuingGame(false);
        potManager.action(player2, new ResolvedAction(DecisionType.CALL, new BigDecimal(1)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(1));
        PriorityQueue<PlayerRanking> playerRanks = new PriorityQueue<>();
        playerRanks.add(new PlayerRanking(player1, 60));
        playerRanks.add(new PlayerRanking(player2, 60));
        playerRanks.add(new PlayerRanking(player3, 80));
        playerRanks.add(new PlayerRanking(player4, 60));
        playerRanks.add(new PlayerRanking(player5, 90));
        playerRanks.add(new PlayerRanking(player6, 60));
        log.info("playerRanks: {}", playerRanks);
        potManager.judge(playerRanks);
        log.info(potManager.getPlayerPrizeStack());
        potManager.judge(playerRanks);
        log.info(playerRanks);
    }

    @Test
    public void testNotALLIN() {
        PotManager potManager = new PotManagerImpl();
        CardPlayer player1 = new SimpleCardPlayer(null, new BigDecimal(5), 1);
        CardPlayer player2 = new SimpleCardPlayer(null, new BigDecimal(6), 2);
        CardPlayer player3 = new SimpleCardPlayer(null, new BigDecimal(3), 3);
        CardPlayer player4 = new SimpleCardPlayer(null, new BigDecimal(6), 4);
        CardPlayer player5 = new SimpleCardPlayer(null, new BigDecimal(6), 5);
        CardPlayer player6 = new SimpleCardPlayer(null, new BigDecimal(6), 6);

        potManager.action(player1, new ResolvedAction(DecisionType.RAISE, new BigDecimal(1)));
        PlayerUtil.collectChipsExactly(player1, new BigDecimal(1));
        potManager.action(player2, new ResolvedAction(DecisionType.RAISE, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(2));
        potManager.action(player3, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player3, new BigDecimal(2));
        potManager.action(player4, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player4, new BigDecimal(2));
        potManager.action(player5, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player5, new BigDecimal(2));
        potManager.action(player6, new ResolvedAction(DecisionType.RAISE, new BigDecimal(5)));
        PlayerUtil.collectChipsExactly(player6, new BigDecimal(5));
        potManager.action(player1, new ResolvedAction(DecisionType.CALL, new BigDecimal(3)));
        PlayerUtil.collectChipsExactly(player1, new BigDecimal(3));
        potManager.action(player2, new ResolvedAction(DecisionType.CALL, new BigDecimal(3)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(3));
        potManager.action(player3, new ResolvedAction(DecisionType.CALL, new BigDecimal(1)));
        PlayerUtil.collectChipsExactly(player3, new BigDecimal(1));
        potManager.action(player4, new ResolvedAction(DecisionType.RAISE, new BigDecimal(4)));
        PlayerUtil.collectChipsExactly(player4, new BigDecimal(4));
        potManager.action(player5, new ResolvedAction(DecisionType.FOLD, new BigDecimal(0)));
        player5.setIsContinuingGame(false);
        potManager.action(player6, new ResolvedAction(DecisionType.FOLD, new BigDecimal(0)));
        player6.setIsContinuingGame(false);
        potManager.action(player2, new ResolvedAction(DecisionType.CALL, new BigDecimal(1)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(1));
        PriorityQueue<PlayerRanking> playerRanks = new PriorityQueue<>();
        playerRanks.add(new PlayerRanking(player1, 60));
        playerRanks.add(new PlayerRanking(player2, 60));
        playerRanks.add(new PlayerRanking(player3, 80));
        playerRanks.add(new PlayerRanking(player4, 60));
        playerRanks.add(new PlayerRanking(player5, 90));
        playerRanks.add(new PlayerRanking(player6, 60));
        try {
            potManager.judge(playerRanks);
        } catch (Exception e) {
            log.error("Exception caught: {}", e.getMessage());
        }
    }
}
