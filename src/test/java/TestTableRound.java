import control.impl.RobotGamePlayerDoPreset;
import control.impl.RobotGamePlayerRandom;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.CardTableImpl;
import table.mechanism.DecisionType;

import java.math.BigDecimal;

public class TestTableRound {

    @Test
    public void testTableRound() {
        CardTable ct = new CardTableImpl();
        TableConfig config = new TableConfig(new BigDecimal(12), new BigDecimal(1));
        ct.setTableConfig(config);
        for (int i = 0; i < 5; i++) {
            RobotGamePlayerDoPreset robotGamePlayerDoPreset = new RobotGamePlayerDoPreset();
            robotGamePlayerDoPreset.setReaction(DecisionType.FOLD, new BigDecimal(0));
            if (i == 0) {
                robotGamePlayerDoPreset.setReaction(DecisionType.CALL, new BigDecimal(0));
            }
            ct.playerJoin(robotGamePlayerDoPreset);
        }
        ct.startRounds();
    }

    @Test
    public void testTableRandomRobots() {
        CardTable ct = new CardTableImpl();
        TableConfig config = new TableConfig(new BigDecimal(12), new BigDecimal(1));
        ct.setTableConfig(config);
        for (int i = 0; i < 5; i++) {
            ct.playerJoin(new RobotGamePlayerRandom());
        }
        ct.startRounds();
    }
}
