import control.player.controller.impl.RobotGamePlayerRandom;
import control.player.impl.RobotGamePlayer;
import org.junit.Test;
import table.config.TableConfig;
import table.player.PlayerList;
import table.player.impl.PlayerCoil;
import table.player.impl.SimpleCardPlayerFactory;
import table.pot.PotManager;
import table.pot.impl.PotManagerImpl;
import table.state.GameState;
import table.state.GameStateContext;

import java.math.BigDecimal;

public class TestState {
    private final PlayerList players;
    private final TableConfig tableConfig;
    private final PotManager potManager;

    public TestState() {
        this.players = new PlayerCoil();
        this.tableConfig = new TableConfig(new BigDecimal(12), new BigDecimal(1));
        SimpleCardPlayerFactory simpleCardPlayerFactory = new SimpleCardPlayerFactory();
        simpleCardPlayerFactory.setConfig(this.tableConfig);
        for (int i = 0; i < 3; i++) {
            this.players.addPlayer(simpleCardPlayerFactory.createCardPlayer(new RobotGamePlayer(new RobotGamePlayerRandom())));
        }
        this.potManager = new PotManagerImpl();
        this.potManager.clearStack();
    }

    @Test
    public void test() {
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
