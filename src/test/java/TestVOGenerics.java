import control.player.controller.PlayerController;
import control.player.impl.RobotGamePlayer;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.ClassicTable;
import table.rule.decision.DecisionRequest;
import table.rule.decision.PlayerDecision;
import table.rule.decision.impl.FoldDecision;
import table.vo.privateinfo.PlayerPrivateVO;
import table.vo.publicinfo.PublicVO;

import java.math.BigDecimal;

@Log4j2
public class TestVOGenerics {
    @Test
    public void testCompleteFlow() {
        CardTable table = new ClassicTable();
        table.playerJoin(new RobotGamePlayer(new PrinterRobot()));
        table.playerJoin(new RobotGamePlayer(new PrinterRobot()));
        TableConfig config = new TableConfig(new BigDecimal(24),
                new BigDecimal(1),
                new BigDecimal(2400),
                22, 1);
        table.setTableConfig(config);
        table.startRounds();
    }
}

@Log4j2
class PrinterRobot implements PlayerController {

    @Override
    public void updatePublicInfo(PublicVO publicVO) {
        log.info(publicVO);
    }

    @Override
    public void updatePrivateInfo(PlayerPrivateVO privateInfoVO) {
        //It doesn't care
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        return new FoldDecision();
    }
}
