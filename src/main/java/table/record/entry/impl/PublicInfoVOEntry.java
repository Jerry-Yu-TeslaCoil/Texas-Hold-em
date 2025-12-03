package table.record.entry.impl;

import lombok.Getter;
import table.record.processor.EntryProcessor;
import table.record.entry.TimelineEntry;
import table.vo.publicinfo.PublicVO;

import java.time.Instant;

@Getter
public class PublicInfoVOEntry extends TimelineEntry {

    private final PublicVO publicVO;

    public PublicInfoVOEntry(Instant time, PublicVO publicVO) {
        super(time);
        this.publicVO = publicVO;
    }

    @Override
    public <T> T accept(EntryProcessor<T> reader) {
        return reader.readEntry(this);
    }
}
