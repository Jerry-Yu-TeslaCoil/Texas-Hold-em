package table.state.gamestate.impl;

import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.config.TableConfig;
import table.mechanism.decision.DecisionType;
import table.mechanism.decision.ResolvedAction;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.PlayerList;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;
import table.vo.privateinfo.PlayerPrivateVO;
import table.vo.publicinfo.TablePublicVO;
import table.vo.publicinfo.builder.PlayerPublicVOBuilder;
import util.PlayerUtil;

import java.math.BigDecimal;

/**
 * Handler enum of the PreFlop state.
 *
 * <p>
 *     This state handler is for bb charge, sb charge, send cards and VO dispatch.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Log4j2
public enum PreFlopStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("Enter PreFlop State");
        PlayerList players = context.getPlayers();
        TableConfig tableConfig = context.getTableConfig();
        BigDecimal bb = tableConfig.halfMinimumBet().multiply(new BigDecimal(2));
        BigDecimal sb = tableConfig.halfMinimumBet();

        chargeBlinds(context, players, sb, bb);

        sendCards(context, players);

        updatePublicVO(context);

        return PlayerUtil.startBet(players.getButtonPosition() + 3, context, GameState.FLOP);
    }

    private static void updatePublicVO(GameStateContext context) {
        TablePublicVO.Builder tablePublicVOBuilder = context.getTablePublicVOBuilder();
        PlayerPublicVOBuilder playerPublicVOBuilder = context.getPlayerPublicVOBuilder();

        tablePublicVOBuilder.setCurrentGameState(GameState.PRE_FLOP);
        playerPublicVOBuilder.setState(GameState.PRE_FLOP);
        PlayerUtil.buildAndPublishVO(context);
    }

    private static void sendCards(GameStateContext context, PlayerList players) {
        PlayerIterator iterator = players.getIterator();
        while (iterator.hasNext()) {
            CardPlayer cardPlayer = iterator.next();
            cardPlayer.addHoleCard(context.getCardDeck().takePeekCard());
            context.getCardDeck().shuffle();
            cardPlayer.addHoleCard(context.getCardDeck().takePeekCard());
            context.getCardDeck().shuffle();
            PlayerPrivateVO playerPrivateVO = new PlayerPrivateVO(cardPlayer.getHoleCards().toArray(new PokerCard[0]));
            cardPlayer.getGamePlayer().getPlayerController().updatePrivateInfo(playerPrivateVO);
        }
    }

    private static void chargeBlinds(GameStateContext context, PlayerList players, BigDecimal sb, BigDecimal bb) {
        CardPlayer smallBlindPlayer = players.getSmallBlindPlayer();
        BigDecimal actualSmallBlind = PlayerUtil.collectChipsAsMuch(smallBlindPlayer, sb);
        log.trace("Charge small blind player {} with {} chips", smallBlindPlayer.toSimpleLogString(),
                actualSmallBlind);
        if (smallBlindPlayer.getIsAllIn()) {
            context.setDecidingPlayerNum(context.getDecidingPlayerNum() - 1);
        }
        CardPlayer bigBlindPlayer = players.getBigBlindPlayer();
        BigDecimal actualBigBlind = PlayerUtil.collectChipsAsMuch(bigBlindPlayer, bb);
        log.trace("Charge big blind player {} with {} chips", bigBlindPlayer.toSimpleLogString(),
                actualBigBlind);
        if (bigBlindPlayer.getIsAllIn()) {
            context.setDecidingPlayerNum(context.getDecidingPlayerNum() - 1);
        }
        context.getPotManager().action(smallBlindPlayer, new ResolvedAction(DecisionType.RAISE, actualSmallBlind));
        context.getPotManager().action(bigBlindPlayer, new ResolvedAction(DecisionType.RAISE, actualBigBlind));
        context.setBetBasisLine(context.getTableConfig().halfMinimumBet().multiply(new BigDecimal(2)));
    }
}