package table.impl;

import control.GamePlayer;
import table.CardTable;
import table.card.CardDeckFactory;
import table.card.impl.NoJokerDeckFactory;
import table.config.TableConfig;
import table.player.CardPlayer;
import table.player.CardPlayerFactory;
import table.player.PlayerList;
import table.player.impl.PlayerCoil;
import table.player.impl.SimpleCardPlayerFactory;
import table.pot.StatisticsPotManager;
import table.pot.impl.PotManagerImpl;
import table.pot.impl.StatisticsPotManagerImpl;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
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
    private TableConfig tableConfig;
    private final CardPlayerFactory playerFactory;
    private final StatisticsPotManager potManager;

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
        CardDeckFactory cardDeckFactory = NoJokerDeckFactory.getInstance();
        this.potManager = new StatisticsPotManagerImpl(new PotManagerImpl());
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
    public ApplicationResult playerJoin(GamePlayer gamePlayer) {
        if (gamePlayer == null) {
            throw new NullPointerException("Given playerController cannot be null");
        }
        CardPlayer cardPlayer = this.playerFactory.createCardPlayer(gamePlayer);
        return this.players.addPlayer(cardPlayer);
    }

    @Override
    public ApplicationResult playerLeave(GamePlayer gamePlayer) {
        if (gamePlayer == null) {
            throw new NullPointerException("Given playerController cannot be null");
        }
        return this.players.removePlayer(gamePlayer);
    }

    @Override
    public void startRounds() {
        GameStateContext gameStateContext = new GameStateContext();
        gameStateContext.setPlayers(new PlayerCoil(this.players));
        gameStateContext.setTableConfig(this.tableConfig);
        gameStateContext.setPotManager(this.potManager);
        gameStateContext.setRoundIndex(0);
        GameState gameState = GameState.INIT;
        //The end condition can be changed and controlled by CardTable, but currently let GameState to control it.
        while (gameState != null) {
            gameState = gameState.execute(gameStateContext);
        }
    }
}
