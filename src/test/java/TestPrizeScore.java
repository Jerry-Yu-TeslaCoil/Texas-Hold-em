import control.gameplayer.impl.RobotGamePlayer;
import control.playercontroller.impl.RobotGamePlayerDoPreset;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.card.PokerCard;
import table.card.Rank;
import table.card.Suit;
import table.card.impl.FixedPokerCard;
import table.mechanism.decision.DecisionType;
import table.mechanism.decision.ResolvedAction;
import table.player.CardPlayer;
import table.player.impl.SimpleCardPlayer;
import table.pot.PlayerRanking;
import table.pot.PotManager;
import table.pot.impl.PotManagerImpl;
import table.pot.impl.StatisticsPotManagerImpl;
import util.PlayerUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Log4j2
public class TestPrizeScore {

    @Test
    public void test() {
        PotManager manager = new StatisticsPotManagerImpl(new PotManagerImpl());
        manager.clearStack();
        List<CardPlayer> players = new ArrayList<>();
        CardPlayer player1 = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerDoPreset()), new BigDecimal(9), 1);
        players.add(player1);
        CardPlayer player2 = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerDoPreset()), new BigDecimal(24), 2);
        players.add(player2);
        CardPlayer player3 = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerDoPreset()), new BigDecimal(2), 3);
        players.add(player3);
        CardPlayer player4 = new SimpleCardPlayer(new RobotGamePlayer(new RobotGamePlayerDoPreset()), new BigDecimal(25), 4);
        players.add(player4);
        player1.addHoleCard(new FixedPokerCard(Suit.clubs, Rank.SEVEN));
        player1.addHoleCard(new FixedPokerCard(Suit.spades, Rank.EIGHT));
        player2.addHoleCard(new FixedPokerCard(Suit.spades, Rank.KING));
        player2.addHoleCard(new FixedPokerCard(Suit.spades, Rank.FIVE));
        player3.addHoleCard(new FixedPokerCard(Suit.hearts, Rank.TEN));
        player3.addHoleCard(new FixedPokerCard(Suit.diamonds, Rank.THREE));
        player4.addHoleCard(new FixedPokerCard(Suit.diamonds, Rank.KING));
        player4.addHoleCard(new FixedPokerCard(Suit.spades, Rank.THREE));
        manager.action(player1, new ResolvedAction(DecisionType.CALL, new BigDecimal(5)));
        PlayerUtil.collectChipsExactly(player1, new BigDecimal(5));
        manager.action(player2, new ResolvedAction(DecisionType.CALL, new BigDecimal(6)));
        PlayerUtil.collectChipsExactly(player2, new BigDecimal(6));
        manager.action(player3, new ResolvedAction(DecisionType.CALL, new BigDecimal(2)));
        PlayerUtil.collectChipsExactly(player3, new BigDecimal(2));
        manager.action(player4, new ResolvedAction(DecisionType.CALL, new BigDecimal(6)));
        PlayerUtil.collectChipsExactly(player4, new BigDecimal(6));
        manager.action(player1, new ResolvedAction(DecisionType.FOLD, new BigDecimal(0)));
        player1.setIsContinuingGame(false);
        manager.action(player4, new ResolvedAction(DecisionType.FOLD, new BigDecimal(0)));
        player4.setIsContinuingGame(false);
        PriorityQueue<PlayerRanking> rankings = new PriorityQueue<>();
        List<PokerCard> cards = new ArrayList<>();
        cards.add(new FixedPokerCard(Suit.clubs, Rank.FIVE));
        cards.add(new FixedPokerCard(Suit.diamonds, Rank.TWO));
        cards.add(new FixedPokerCard(Suit.clubs, Rank.FOUR));
        cards.add(new FixedPokerCard(Suit.clubs, Rank.JACK));
        cards.add(new FixedPokerCard(Suit.spades, Rank.JACK));
        for (CardPlayer player : players) {
            rankings.add(new PlayerRanking(player, cards));
        }
        log.info(rankings);
        manager.judge(rankings);
        log.info(manager.getPlayerPrizeStack());
    }
}


/*
Table: TablePublicVO{basicBet=2, initialBet=12, currentGameState=SHOWDOWN,
        currentDecisionMakerId=3, madeDecision=ResolvedAction[decisionType=FOLD, value=0], publicCards=[5_OF_clubs, 2_OF_diamonds, 4_OF_clubs, J_OF_clubs, J_OF_spades]}
Pot: PotPublicVO[allInvestedChips=19]
Players (5):
        1. PlayerPublicVO[playerPersonalVO=PlayerPersonalVO[], pokerCard=[7_OF_clubs, 8_OF_spades],
chipsInHand=4, chipsInvested=5, isContinuingGame=false, prize=null]
        2. PlayerPublicVO[playerPersonalVO=PlayerPersonalVO[], pokerCard=[K_OF_spades, 5_OF_spades],
chipsInHand=18, chipsInvested=6, isContinuingGame=true, prize=null]
        3. PlayerPublicVO[playerPersonalVO=PlayerPersonalVO[], pokerCard=[10_OF_hearts, 3_OF_diamonds],
chipsInHand=0, chipsInvested=2, isContinuingGame=true, prize=null]
        4. PlayerPublicVO[playerPersonalVO=PlayerPersonalVO[], pokerCard=[K_OF_diamonds, 3_OF_spades],
chipsInHand=19.00, chipsInvested=6, isContinuingGame=false, prize=null]
        5. PlayerPublicVO[playerPersonalVO=PlayerPersonalVO[], pokerCard=null,
chipsInHand=0, chipsInvested=0, isContinuingGame=false, prize=null]
*/