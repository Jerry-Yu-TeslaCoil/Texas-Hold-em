package table.state.gamestate.impl;

import lombok.extern.log4j.Log4j2;
import table.config.TableConfig;
import table.mechanism.DecisionType;
import table.mechanism.ResolvedAction;
import table.player.CardPlayer;
import table.player.PlayerList;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;
import util.PlayerUtil;

import java.math.BigDecimal;

/**
 * Handler enum of the PreFlop state.
 *
 * <p>
 *     This state handler is for bb charge and sb charge and such.
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
        PlayerList players = context.getPlayers();
        TableConfig tableConfig = context.getTableConfig();
        BigDecimal bb = tableConfig.halfMinimumBet().multiply(new BigDecimal(2));
        BigDecimal sb = tableConfig.halfMinimumBet();
        CardPlayer bigBlindPlayer = players.getBigBlindPlayer();
        BigDecimal actualBigBlind = PlayerUtil.collectChipsAsMuch(bigBlindPlayer, bb);
        log.trace("Charge big blind player {} with {} chips", bigBlindPlayer.toSimpleLogString(),
                actualBigBlind);
        CardPlayer smallBlindPlayer = players.getSmallBlindPlayer();
        BigDecimal actualSmallBlind = PlayerUtil.collectChipsAsMuch(smallBlindPlayer, sb);
        log.trace("Charge big blind player {} with {} chips", smallBlindPlayer.toSimpleLogString(),
                actualSmallBlind);
        context.getPotManager().action(smallBlindPlayer, new ResolvedAction(DecisionType.RAISE, actualSmallBlind));
        context.getPotManager().action(bigBlindPlayer, new ResolvedAction(DecisionType.RAISE, actualBigBlind));
        //TODO: Send cards, refresh players' visible info
        return GameState.FLOP;
    }
}