import control.player.GamePlayer;
import control.player.controller.impl.RobotGamePlayerRandom;
import control.player.impl.RobotGamePlayer;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.ClassicTable;
import table.player.CardPlayerFactory;
import table.player.impl.SimpleCardPlayerFactory;
import table.rule.decision.DecisionType;
import table.rule.decision.ResolvedAction;
import table.state.GameState;
import table.vo.publicinfo.PlayerPublicVO;
import table.vo.publicinfo.PotPublicVO;
import table.vo.publicinfo.PublicVO;
import table.vo.publicinfo.TablePublicVO;
import table.vo.publicinfo.builder.PlayerPublicVOBuilder;
import table.vo.publicinfo.builder.impl.ClassicPlayerPublicVOBuilder;

import java.math.BigDecimal;

@Log4j2
public class TestVOBuild {

    @Test
    public void test() {
        TableConfig tableConfig = new TableConfig(new BigDecimal(24), new BigDecimal(1));
        CardTable cardTable = new ClassicTable(tableConfig);
        GamePlayer ai = new RobotGamePlayer(new RobotGamePlayerRandom());
        cardTable.playerJoin(ai);
        TablePublicVO.Builder tablePublicVO = TablePublicVO.builder();
        tablePublicVO = tablePublicVO.setPublicCards(null)
                .setBasicBet(tableConfig.halfMinimumBet())
                .setInitialBet(tableConfig.initBet())
                .setCurrentDecisionMakerId(0)
                .setCurrentGameState(GameState.INIT)
                .setMadeDecision(new ResolvedAction(DecisionType.CALL, new BigDecimal(1)));
        PlayerPublicVOBuilder builder = new ClassicPlayerPublicVOBuilder();
        builder.setState(GameState.INIT);
        CardPlayerFactory playerFactory = new SimpleCardPlayerFactory();
        playerFactory.setConfig(tableConfig);
        PublicVO publicVO = new PublicVO(tablePublicVO.build(),
                new PlayerPublicVO[]{builder.build(playerFactory.createCardPlayer(ai))},
                new PotPublicVO(new BigDecimal(5)));
        log.info("{}", publicVO);
    }
}
