import control.PlayerController;
import control.impl.RobotPlayerCallOnly;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.CardTableImpl;
import util.ApplicationResult;

import java.math.BigDecimal;

@Log4j2
public class TestInitTable {

    @Test
    public void testInitTable() {
        CardTable cardTable = new CardTableImpl();
        TableConfig tableConfig = new TableConfig(
                new BigDecimal(24),
                new BigDecimal(1),
                new BigDecimal(24),
                22);
        cardTable.setTableConfig(tableConfig);
        PlayerController controller = new RobotPlayerCallOnly();
        ApplicationResult applicationResult = cardTable.playerJoin(controller);
        log.info(applicationResult.isResult());
    }
}
