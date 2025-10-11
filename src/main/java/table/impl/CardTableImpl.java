package table.impl;

import control.PlayerController;
import table.CardTable;
import table.card.CardDeck;
import table.card.impl.NoJokerDeckFactory;
import table.config.TableConfig;
import table.player.CardPlayer;
import table.player.PlayerList;
import table.player.impl.PlayerCoil;
import util.ApplicationResult;

import java.math.BigDecimal;

/**
 * The simplest card table, conducting usual game rounds.
 *
 * <p>
 *     Simply construct an instance and call startRound() would start a game.
 * </p>
 *
 * <p>
 *     Currently, this is considered not thread-safe.
 *     TODO: Do reconstruct this class to thead-safe version to support multi-table platform.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class CardTableImpl implements CardTable {
    private BigDecimal bigBlindValue;
    private final PlayerList players;
    private NoJokerDeckFactory noJokerDeckFactory;
    private TableConfig tableConfig;

    /**
     * Construct a table with no max player num limit appointed.
     * The max player num will be set as 22 players same as usual rule.
     */
    public CardTableImpl() {
        this(22);
    }

    /**
     * Appoint a max player num to construct a table.
     * @param maxPlayers The max number of card players.
     */
    public CardTableImpl(int maxPlayers) {
        this.players = new PlayerCoil();
        this.players.setMaxPlayers(maxPlayers);
    }

    @Override
    public void setTableConfig(TableConfig tableConfig) {
        this.tableConfig = tableConfig;
        this.players.setMaxPlayers(tableConfig.maxPlayers());
    }

    @Override
    public TableConfig getTableConfig() {
        return this.tableConfig;
    }

    @Override
    public ApplicationResult playerJoin(PlayerController cardPlayer) {
        if (cardPlayer == null) {
            throw new NullPointerException("Given cardPlayer cannot be null");
        }
        //TODO: Not just a construct method, but a builder or a factory
        //return this.players.addPlayer(cardPlayer);
        return null;
    }

    @Override
    public ApplicationResult playerLeave(PlayerController cardPlayer) {
        if (cardPlayer == null) {
            throw new NullPointerException("Given cardPlayer cannot be null");
        }
        //TODO: Remove by the playerController
        //return this.players.removePlayer(cardPlayer);
        return null;
    }

    @Override
    public void startRounds() {
        //TODO: Do use state and handler
        CardDeck cardDeck = noJokerDeckFactory.getCardDeck();
        System.out.println(this.players.getPlayerNum() + " players have joined the table.");
        System.out.println("Starting with small blind.");
        System.out.println("Big blind.");
        System.out.println("Deal");
        //Deal cards.
        for (CardPlayer cardPlayer : this.players.getPlayers()) {
            cardPlayer.addHoleCard(cardDeck.takePeekCard());
            cardPlayer.addHoleCard(cardDeck.takePeekCard());
        }
        //Everyone make a round decision. Will engage from small_blind to the last one.
    }
}
