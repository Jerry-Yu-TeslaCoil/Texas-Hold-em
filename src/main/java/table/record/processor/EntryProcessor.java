package table.record.processor;

import table.record.entry.impl.PlayerPrivateInfoVOEntry;
import table.record.entry.impl.PublicInfoVOEntry;

public interface EntryProcessor<T> {
    T readEntry(PublicInfoVOEntry publicInfoVOEntry);

    T readEntry(PlayerPrivateInfoVOEntry playerPrivateInfoVOEntry);
}
