package table.record.entry.impl;

import lombok.Getter;
import table.record.processor.EntryProcessor;
import table.record.entry.TimelineEntry;
import table.vo.privateinfo.PlayerPrivateVO;

import java.time.Instant;

/**
 * The TimelineEntry containing PlayerPrivateVO and its published time.
 *
 * @author jerry
 *
 * @version 1.1
 */
@Getter
public class PlayerPrivateInfoVOEntry extends TimelineEntry {

    private final PlayerPrivateVO playerPrivateVO;

    /**
     * Create an entry with instant and data.
     * @param time The instant recorded.
     * @param playerPrivateVO Recorded VO data.
     */
    public PlayerPrivateInfoVOEntry(Instant time, PlayerPrivateVO playerPrivateVO) {
        super(time);
        this.playerPrivateVO = playerPrivateVO;
    }

    @Override
    public <T> T accept(EntryProcessor<T> reader) {
        return reader.readEntry(this);
    }
}
