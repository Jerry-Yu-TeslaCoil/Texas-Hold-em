package table.player;

import java.util.Iterator;

/**
 * The iterator of the player list.
 *
 * <p>
 *     This is used for a more elegant way to conduct the process of the game.
 *     A single-round iterator naturally supports the mechanism of the game, like call and raise round.
 *     Create a new class implementing this interface to support different rules.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public abstract class PlayerIterator implements Iterator<CardPlayer> {

    /**
     * Jump to the appointed player by round index.
     * @param position The index of the player in the PLAYER LIST.
     */
    public abstract void jumpTo(int position);

    /**
     * Get the current position of the iterator.
     * @return The position of the current pointing player.
     */
    public abstract int getCurrentPosition();

    /**
     * Get the start position of this iterator.
     * This is often used to know who is the state-changer, like the one raising bet value.
     * @return The starter's position.
     */
    public abstract int getStartPosition();
}
