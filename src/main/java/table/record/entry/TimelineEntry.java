package table.record.entry;

import lombok.Getter;
import table.record.processor.EntryProcessor;

import java.time.Instant;

/**
 * A package class for containing replay VO entry and sealed to a list.
 *
 * <p>
 *     The TimelineEntry should contain the instant the VO is published,
 *     meanwhile, as a visited element, it's accept() method is used only for visitor(parser).
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
@Getter
public abstract class TimelineEntry {
    private final Instant time;

    /**
     * Construct an entry with recording time.
     * @param time The instant that VO publish takes place.
     */
    public TimelineEntry(Instant time) {
        this.time = time;
    }

    /**
     * Visiting method for visitor.
     * BE WARNED: Typically this should not be called directly.
     * @param reader The visitor or parser.
     * @return The parsed data.
     * @param <T> Class of the data. Should be decided by visitor.
     */
    public abstract <T> T accept(EntryProcessor<T> reader);
}
