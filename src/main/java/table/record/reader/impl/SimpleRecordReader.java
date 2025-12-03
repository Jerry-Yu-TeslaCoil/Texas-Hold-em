package table.record.reader.impl;

import control.playeridentifier.PlayerIdentifier;
import table.record.entry.TimelineEntry;
import table.record.processor.EntryProcessor;
import table.record.reader.RecordReader;

import java.util.List;

public class SimpleRecordReader implements RecordReader {
    private final PlayerIdentifier playerIdentifier;
    private final List<TimelineEntry> entries;
    private int currentIndex;

    public SimpleRecordReader(List<TimelineEntry> entries, PlayerIdentifier playerIdentifier) {
        this.entries = entries;
        this.playerIdentifier = playerIdentifier;
        this.currentIndex = 0;
    }

    @Override
    public PlayerIdentifier getPlayerIdentifier() {
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
