import control.player.controller.impl.RobotGamePlayerDoPreset;
import control.player.impl.RobotGamePlayer;
import org.junit.Test;
import table.CardTable;
import table.impl.ClassicTable;
import table.rule.decision.DecisionType;

import java.math.BigDecimal;

public class TestAbnormalExit {
    CardTable table = new ClassicTable();

    @Test
    public void testAllInExit() {
        for (int i = 0; i < 5; i++) {
            RobotGamePlayerDoPreset raiseRobot = new RobotGamePlayerDoPreset();
            raiseRobot.setReaction(DecisionType.RAISE, new BigDecimal(10));
            table.playerJoin(new RobotGamePlayer(raiseRobot));
        }
        table.startRounds();
    }
}
