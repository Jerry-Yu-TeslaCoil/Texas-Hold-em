package table.state.impl;

import lombok.extern.log4j.Log4j2;
import table.card.CardDeck;
import table.card.PokerCard;
import table.card.impl.NoJokerDeckFactory;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.PlayerList;
import table.pot.PotManager;
import table.state.GameState;
import table.state.GameStateContext;
import table.state.GameStateHandler;
import table.vo.publicinfo.TablePublicVO;
import table.vo.publicinfo.builder.impl.ClassicPlayerPublicVOBuilder;
import util.PlayerUtil;

import java.math.BigDecimal;

/**
 * Handler enum of the init state.
 *
 * <p>
 *     This state handler is used for initialization of the round.
 *     For example, double check if the pot manager is cleared for the next game, or create a deck of cards.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Log4j2
public enum InitStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("*************** Starting Game State ***************");
        log.info("Enter Init State");
        initPlayerList(context);
        initPotManager(context);
        initCardDeck(context);
        initTableConfig(context);
        initVOBuilders(context);

        PlayerUtil.buildAndPublishVO(context);

        context.setBetBasisLine(BigDecimal.ZERO);
        context.setRoundIndex(context.getRoundIndex() + 1);

        PlayerIterator iterator = context.getPlayers().getIterator();
        int stayedPlayerNum = 0;
        while (iterator.hasNext()) {
            if (!iterator.next().getStack().equals(new BigDecimal(0))) {
                stayedPlayerNum++;
            }
        }

        if (context.getRoundIndex() >= context.getTotalRounds()) {
            log.info("Game end as required round finished.");
            return null;
        }

        if (stayedPlayerNum < 2) {
            log.info("Game end as one of the player made a complete win.");
            return null;
        }

        return GameState.PRE_FLOP;
    }

    private static void initVOBuilders(GameStateContext context) {
        context.setPublicCards(new PokerCard[5]);

        context.setPlayerPublicVOBuilder(new ClassicPlayerPublicVOBuilder());
        context.setTablePublicVOBuilder(TablePublicVO.builder());

        context.getTablePublicVOBuilder().setInitialBet(context.getTableConfig().initBet())
                .setBasicBet(context.getTableConfig().halfMinimumBet().multiply(new BigDecimal(2)))
                .setCurrentGameState(GameState.INIT)
                .setCurrentDecisionMakerId(-1)
                .setMadeDecision(null)
                .setPublicCards(null);
        context.getPlayerPublicVOBuilder().setState(GameState.INIT);
    }

    private static void initTableConfig(GameStateContext context) {
        if (context.getTableConfig() == null) {
            throw new RuntimeException("Table config is null");
        }
    }

    private static void initCardDeck(GameStateContext context) {
        CardDeck cardDeck = NoJokerDeckFactory.getInstance().getCardDeck();
        cardDeck.shuffle();
        context.setCardDeck(cardDeck);
    }

    private static void initPotManager(GameStateContext context) {
        PotManager potManager = context.getPotManager();
        if (potManager == null) {
            throw new RuntimeException("Pot manager is null");
        }
        potManager.clearStack();
        context.setPotJudged(false);
    }

    private static void initPlayerList(GameStateContext context) {
        PlayerList players = context.getPlayers();
        if (players == null) {
            throw new RuntimeException("Player list is null");
        }
        int activePlayerNum = 0;
        players.nextRound();
        PlayerIterator iterator = players.getIterator();
        while (iterator.hasNext()) {
            CardPlayer nextPlayer = iterator.next();
            nextPlayer.clearState();
            if (nextPlayer.getStack().compareTo(BigDecimal.ZERO) == 0) {
                nextPlayer.setIsContinuingGame(false);
            } else {
                activePlayerNum++;
            }
        }
        context.setActivePlayerNum(activePlayerNum);
        context.setDecidingPlayerNum(activePlayerNum);
    }
}