package table.player;

import java.util.Iterator;

public abstract class PlayerIterator implements Iterator<CardPlayer> {
    public abstract void jumpTo(int position);

    public abstract int getCurrentPosition();

    public abstract int getStartPosition();

    public abstract int getModifiedPlayerNum();
}
