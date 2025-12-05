package table.record.recorder;

import table.record.entry.TimelineEntry;
import table.record.reader.RecordReader;

/**
 * Recorder of the game VOs.
 *
 * <p>
 *     In contrast to RecordReader, this is write-only.
 *     After the game finishes, call getRecordReader() to get a read-only replay.
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
public interface GameRecorder {

    /**
     * Record an entry of VO.
     * @param entry The recorded VO entry.
     */
    void record(TimelineEntry entry);

    /**
     * Clear all records.
     */
    void clear();

    /**
     * Get replay as a RecordReader.
     * @return A RecordReader with player's identifier and all the replay entries.
     */
    RecordReader getRecordReader();
}
