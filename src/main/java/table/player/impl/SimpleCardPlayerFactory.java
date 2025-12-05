package table.player.impl;

import control.player.GamePlayer;
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

    private int id = 0;
    private BigDecimal initBet;

    private final Object lock = new Object();

    @Override
    public void setConfig(TableConfig config) {
        this.initBet = config.initBet();
    }

    @Override
    public CardPlayer createCardPlayer(GamePlayer gamePlayer) {
        int chosenID;
        synchronized (lock) {
            id++;
            chosenID = id;
        }
        return new SimpleCardPlayer(gamePlayer, initBet, chosenID);
    }

    @Override
    public void resetId() {
        id = 0;
    }
}
