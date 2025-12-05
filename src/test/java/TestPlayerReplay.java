import control.player.impl.RobotGamePlayer;
import control.player.controller.impl.RobotGamePlayerRandom;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.CardTable;
import table.config.TableConfig;
import table.impl.ClassicTable;
import table.record.entry.impl.PlayerPrivateInfoVOEntry;
import table.record.entry.impl.PublicInfoVOEntry;
import table.record.processor.EntryProcessor;
import table.record.reader.RecordReader;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
public class TestPlayerReplay {

    @Test
    public void testPlayerReplay() {
        CardTable ct = new ClassicTable();
        TableConfig config = new TableConfig(new BigDecimal(12), new BigDecimal(1));
        ct.setTableConfig(config);
        for (int i = 0; i < 5; i++) {
            ct.playerJoin(new RobotGamePlayer(new RobotGamePlayerRandom()));
        }
        List<RecordReader> recordReaders = ct.startRounds();
        RecordReader recordReader = recordReaders.get(0);
        EntryProcessor<String> entryProcessor = new EntryProcessor<>() {

            @Override
            public String readEntry(PublicInfoVOEntry publicInfoVOEntry) {
                return publicInfoVOEntry.getTime() + "\n" + publicInfoVOEntry.getPublicVO().toString();
            }

            @Override
            public String readEntry(PlayerPrivateInfoVOEntry playerPrivateInfoVOEntry) {
                return playerPrivateInfoVOEntry.getTime() + "\n" + playerPrivateInfoVOEntry.getPlayerPrivateVO().toString();
            }
        };
        String p;
        do {
            p = recordReader.readNext(entryProcessor);
            log.info(p);
        } while (p != null);
    }
}
