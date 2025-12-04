import control.gameplayer.GamePlayer;
import control.gameplayer.impl.RobotGamePlayer;
import control.playercontroller.impl.RobotGamePlayerRandom;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.CardTableImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Log4j2
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

    @Test
    public void testMultiThreadTableJoinLeave() throws InterruptedException {
        CardTable table = new CardTableImpl();
        GamePlayer gamePlayer = new RobotGamePlayer(new RobotGamePlayerRandom());
        Thread joinThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                table.playerJoin(gamePlayer);
                log.info("Player {} joined", i);
            }
        });
        Thread leaveThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                table.playerLeave(gamePlayer);
                log.info("Player {} leave", i);
            }
        });
        joinThread.start();
        leaveThread.start();
        joinThread.join();
        leaveThread.join();
    }
}
