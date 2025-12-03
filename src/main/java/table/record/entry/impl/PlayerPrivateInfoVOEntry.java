package table.record.entry.impl;

import lombok.Getter;
import table.record.processor.EntryProcessor;
import table.record.entry.TimelineEntry;
import table.vo.privateinfo.PlayerPrivateVO;

import java.time.Instant;

@Getter
public class PlayerPrivateInfoVOEntry extends TimelineEntry {

    private final PlayerPrivateVO playerPrivateVO;

    public PlayerPrivateInfoVOEntry(Instant time, PlayerPrivateVO playerPrivateVO) {
        super(time);
        this.playerPrivateVO = playerPrivateVO;
    }

    @Override
    public <T> T accept(EntryProcessor<T> reader) {
        return reader.readEntry(this);
    }
}
