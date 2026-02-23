import control.player.GamePlayer;
import control.player.controller.impl.RobotGamePlayerCallOnly;
import control.player.impl.RobotGamePlayer;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.ClassicTable;
import util.ApplicationResult;

import java.math.BigDecimal;

@Log4j2
public class TestInitTable {

    @Test
    public void testInitTable() {
        CardTable cardTable = new ClassicTable();
        TableConfig tableConfig = new TableConfig(
                new BigDecimal(24),
                new BigDecimal(1),
                new BigDecimal(24),
                22, 5);
        cardTable.setTableConfig(tableConfig);
        GamePlayer controller = new RobotGamePlayer(new RobotGamePlayerCallOnly());
        ApplicationResult applicationResult = cardTable.playerJoin(controller);
        log.info(applicationResult.isResult());
    }
}
