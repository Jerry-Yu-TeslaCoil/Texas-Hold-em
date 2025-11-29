package table.player.impl;

import control.gameplayer.GamePlayer;
import table.config.TableConfig;
import table.player.CardPlayer;
import table.player.CardPlayerFactory;

import java.math.BigDecimal;

/**
 * The factory class for SimpleCardPlayer.
 *
 * <p>
 *     This factory fills the CardPlayer's stacks as TableConfig.initBet.
 *     And it will allocate an id for each CardPlayer.
 * </p>
 *
 * <p>
 *     As id maintaining, this is not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class SimpleCardPlayerFactory implements CardPlayerFactory {

    private int id;
    private BigDecimal initBet;

    @Override
    public void setConfig(TableConfig config) {
        this.initBet = config.initBet();
    }

    @Override
    public CardPlayer createCardPlayer(GamePlayer controller) {
        return new SimpleCardPlayer(controller, initBet, id++);
    }

    @Override
    public void resetId() {
        id = 0;
    }
}
