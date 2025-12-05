package table.record.recorder.impl;

import control.player.identifier.PlayerIdentifier;
import table.record.entry.TimelineEntry;
import table.record.reader.RecordReader;
import table.record.reader.impl.ArrayRecordReader;
import table.record.recorder.GameRecorder;

import java.util.LinkedList;
import java.util.List;

/**
 * An implementation of the GameRecorder.
 *
 * <p>
 *     This implementation is basically a LinkedList with an adder.
 *     The replay entries are added to the rear when calling record().
 *     So in a multi-thread environment, this is not safe.
 *     Do not use this in a multi-thread circumstance.
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
public class LinkedGameRecorder implements GameRecorder {

    private final PlayerIdentifier<?> playerIdentifier;

    private final List<TimelineEntry> entries = new LinkedList<>();

    /**
     * Create a LinkedGameRecorder with certain player's identifier.
     * @param playerIdentifier The corresponding player's identifier.
     */
    public LinkedGameRecorder(PlayerIdentifier<?> playerIdentifier) {
        this.playerIdentifier = playerIdentifier;
    }

    @Override
    public void record(TimelineEntry entry) {
        entries.add(entry);
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public RecordReader getRecordReader() {
        return new ArrayRecordReader(entries, playerIdentifier);
    }
}
