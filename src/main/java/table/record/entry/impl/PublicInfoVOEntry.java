package table.record.entry.impl;

import lombok.Getter;
import table.record.entry.TimelineEntry;
import table.record.processor.EntryProcessor;
import table.vo.publicinfo.PublicVO;

import java.time.Instant;

/**
 * The TimelineEntry containing the recorded time and PublicInfoVO.
 *
 * @author jerry
 *
 * @version 1.1
 */
@Getter
public class PublicInfoVOEntry extends TimelineEntry {

    private final PublicVO publicVO;

    /**
     * Create an entry with instant and data.
     * @param time The recorded time.
     * @param publicVO The Recorded VO data.
     */
    public PublicInfoVOEntry(Instant time, PublicVO publicVO) {
        super(time);
        this.publicVO = publicVO;
    }

    @Override
    public <T> T accept(EntryProcessor<T> reader) {
        return reader.readEntry(this);
    }
}
