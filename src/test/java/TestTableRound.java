import control.player.controller.impl.RobotGamePlayerDoPreset;
import control.player.controller.impl.RobotGamePlayerRandom;
import control.player.impl.RobotGamePlayer;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.ClassicTable;
import table.rule.decision.DecisionType;

import java.math.BigDecimal;

public class TestTableRound {

    @Test
    public void testTableRound() {
        CardTable ct = new ClassicTable();
        TableConfig config = new TableConfig(new BigDecimal(12), new BigDecimal(1));
        ct.setTableConfig(config);
        for (int i = 0; i < 5; i++) {
            RobotGamePlayerDoPreset robotGamePlayerDoPreset = new RobotGamePlayerDoPreset();
            robotGamePlayerDoPreset.setReaction(DecisionType.FOLD, new BigDecimal(0));
            if (i == 0) {
                robotGamePlayerDoPreset.setReaction(DecisionType.CALL, new BigDecimal(0));
            }
            ct.playerJoin(new RobotGamePlayer(robotGamePlayerDoPreset));
        }
        ct.startRounds();
    }

    @Test
    public void testTableRandomRobots() {
        CardTable ct = new ClassicTable();
        TableConfig config = new TableConfig(new BigDecimal(12), new BigDecimal(1));
        ct.setTableConfig(config);
        for (int i = 0; i < 5; i++) {
            ct.playerJoin(new RobotGamePlayer(new RobotGamePlayerRandom()));
        }
        ct.startRounds();
    }
}
