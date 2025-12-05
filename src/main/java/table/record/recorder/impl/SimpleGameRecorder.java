package table.record.recorder.impl;

import control.player.identifier.PlayerIdentifier;
import table.record.entry.TimelineEntry;
import table.record.reader.RecordReader;
import table.record.reader.impl.SimpleRecordReader;
import table.record.recorder.GameRecorder;

import java.util.LinkedList;
import java.util.List;

public class SimpleGameRecorder implements GameRecorder {

    private final PlayerIdentifier playerIdentifier;

    private final List<TimelineEntry> entries = new LinkedList<>();

    public SimpleGameRecorder(PlayerIdentifier playerIdentifier) {
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
        return new SimpleRecordReader(entries, playerIdentifier);
    }
}
