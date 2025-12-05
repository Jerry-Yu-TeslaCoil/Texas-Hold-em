package table.record.reader.impl;

import control.player.identifier.PlayerIdentifier;
import table.record.entry.TimelineEntry;
import table.record.processor.EntryProcessor;
import table.record.reader.RecordReader;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of RecordReader.
 *
 * <p>
 *     This implementation is basically an ArrayList of TimelineEntry.
 *     When no replay left, readNext() returns null.
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
public class ArrayRecordReader implements RecordReader {
    private final PlayerIdentifier<?> playerIdentifier;
    private final List<TimelineEntry> entries;
    private int currentIndex;

    /**
     * Create a RecordReader with list of entries and identifier.
     * This is used for CardTable to transform entries from form GameRecorder to form RecordReader.
     * @param entries The list of entries as replay VO.
     * @param playerIdentifier The corresponding player identifier.
     */
    public ArrayRecordReader(List<TimelineEntry> entries, PlayerIdentifier<?> playerIdentifier) {
        this.entries = new ArrayList<>(entries);
        this.playerIdentifier = playerIdentifier;
        this.currentIndex = 0;
    }

    @Override
    public PlayerIdentifier<?> getPlayerIdentifier() {
        return playerIdentifier;
    }

    @Override
    public <T> T readNext(EntryProcessor<T> processor) {
        if (currentIndex >= entries.size()) {
            return null;
        }
        return entries.get(currentIndex++).accept(processor);
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }
}
