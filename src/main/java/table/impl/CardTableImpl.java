package table.impl;

import control.PlayerController;
import table.CardTable;
import table.card.CardDeck;
import table.card.CardDeckFactory;
import table.card.impl.NoJokerDeckFactory;
import table.config.TableConfig;
import table.player.CardPlayer;
import table.player.CardPlayerFactory;
import table.player.PlayerList;
import table.player.impl.PlayerCoil;
import table.player.impl.SimpleCardPlayerFactory;
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
    private final PlayerList players;
    private final CardDeckFactory cardDeckFactory;
    private TableConfig tableConfig;
    private final CardPlayerFactory playerFactory;

    /**
     * Construct a table with no max player num limit appointed.
     * The max player num will be set as 22 players same as usual rule.
     */
    public CardTableImpl() {
        this(new TableConfig(new BigDecimal(24), new BigDecimal(1), new BigDecimal(24), 22));
    }

    /**
     * Appoint a max player num to construct a table.
     * @param tableConfig The max number of card players.
     */
    public CardTableImpl(TableConfig tableConfig) {
        this.players = new PlayerCoil();
        this.tableConfig = tableConfig;
        this.players.setMaxPlayers(tableConfig.maxPlayers());
        this.playerFactory = new SimpleCardPlayerFactory();
        this.playerFactory.setConfig(tableConfig);
        this.cardDeckFactory = NoJokerDeckFactory.getInstance();
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
    public ApplicationResult playerJoin(PlayerController playerController) {
        if (playerController == null) {
            throw new NullPointerException("Given playerController cannot be null");
        }
        CardPlayer cardPlayer = this.playerFactory.createCardPlayer(playerController);
        return this.players.addPlayer(cardPlayer);
    }

    @Override
    public ApplicationResult playerLeave(PlayerController playerController) {
        if (playerController == null) {
            throw new NullPointerException("Given playerController cannot be null");
        }
        return this.players.removePlayer(playerController);
    }

    @Override
    public void startRounds() {
        //TODO: Do use state and handler
        CardDeck cardDeck = cardDeckFactory.getCardDeck();
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
