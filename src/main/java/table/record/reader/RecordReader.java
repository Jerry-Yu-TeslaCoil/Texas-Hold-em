package table.record.reader;

import control.player.identifier.PlayerIdentifier;
import table.record.processor.EntryProcessor;

/**
 * The replay interface that's read-only.
 *
 * <p>
 *     This will be given as a List&lt;RecordReader&gt; return value of cardTable.startRounds(),
 *     containing PlayerIdentifier and the player's VO record.
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
public interface RecordReader {

    /**
     * Get the player's identifier. To make correspond between replay and the certain player.
     * @return The player's identifier.
     */
    PlayerIdentifier<?> getPlayerIdentifier();

    /**
     * Read the current entry using the given processor and switch to the next entry.
     * @param processor The parser processor.
     * @return The processor's return value.
     * @param <T> The class of wanted data class.
     */
    <T> T readNext(EntryProcessor<T> processor);

    /**
     * Set the cursor to the head of replay.
     */
    void reset();
}
