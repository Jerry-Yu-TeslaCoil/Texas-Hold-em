import control.gameplayer.impl.RobotGamePlayer;
import control.playercontroller.impl.RobotGamePlayerRandom;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.CardTableImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestMultiThread {

    @Test
    public void test() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CardTable table = new CardTableImpl();
            TableConfig config = new TableConfig(new BigDecimal(24), new BigDecimal(1));
            table.setTableConfig(config);
            for (int j = 0; j < 5; j++) {
                table.playerJoin(new RobotGamePlayer(new RobotGamePlayerRandom()));
            }
            Thread thread = new Thread(table::startRounds);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
