package table.record.reader;

import control.playeridentifier.PlayerIdentifier;
import table.record.processor.EntryProcessor;

public interface RecordReader {
    PlayerIdentifier getPlayerIdentifier();
    <T> T readNext(EntryProcessor<T> processor);
    void reset();
}
