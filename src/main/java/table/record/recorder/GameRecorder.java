package table.record.recorder;

import table.record.entry.TimelineEntry;
import table.record.reader.RecordReader;

public interface GameRecorder {
    void record(TimelineEntry entry);
    void clear();
    RecordReader getRecordReader();
}
