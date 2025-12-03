package table.record.entry;

import lombok.Getter;
import table.record.processor.EntryProcessor;

import java.time.Instant;

@Getter
public abstract class TimelineEntry {
    private final Instant time;

    public TimelineEntry(Instant time) {
        this.time = time;
    }

    public abstract <T> T accept(EntryProcessor<T> reader);
}
