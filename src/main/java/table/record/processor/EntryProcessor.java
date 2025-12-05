package table.record.processor;

import table.record.entry.impl.PlayerPrivateInfoVOEntry;
import table.record.entry.impl.PublicInfoVOEntry;

/**
 * The visitor for the replay.
 *
 * <p>
 *     This class, as a visitor or a parser, can be provided to a RecordReader.
 *     The RecordReader as an iterator will process TimelineEntry one by one.
 *     To access a successful visit, implement the function pointing different subclasses of entry.
 * </p>
 * @param <T>
 *
 * @author jerry
 *
 * @version 1.1
 */
public interface EntryProcessor<T> {

    /**
     * Parse PublicInfoVOEntry.
     * @param publicInfoVOEntry An entry in the RecordReader.
     * @return Data in any class as needed.
     */
    T readEntry(PublicInfoVOEntry publicInfoVOEntry);

    /**
     * Parse PlayerPrivateInfoVOEntry.
     * @param playerPrivateInfoVOEntry An entry in the RecordReader.
     * @return Data in any class as needed.
     */
    T readEntry(PlayerPrivateInfoVOEntry playerPrivateInfoVOEntry);
}
