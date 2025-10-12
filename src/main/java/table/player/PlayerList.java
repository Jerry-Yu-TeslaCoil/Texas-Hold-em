package table.player;

import control.PlayerController;
import util.ApplicationResult;

import java.util.List;

/**
 * Player list of the table.
 *
 * <p>
 *     Last edition, table maintains a list of players, more specifically, an ArrayList.
 *     To further separate the functionality, the PlayerList is introduced to be in charge of CardPlayer management.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PlayerList {

    /**
     * Get the current number of joined players.
     * @return The player number.
     */
    int getPlayerNum();

    /**
     * Get if the player list is empty.
     * @return If the player list is empty.
     */
    boolean isEmpty();

    /**
     * Get player by position index.
     * @param position The position index of the player.
     * @return The player on the appointed position.
     */
    CardPlayer getPlayerAt(int position);

    /**
     * Start the next round of the game.
     * This will operate player list to respond by mechanism.
     */
    void nextRound();

    /**
     * Get the position of the button player.
     * The button position is the position of the player who has the button in his hand at the start of the round.
     * @return The position of the button player.
     */
    int getButtonPosition();

    /**
     * Get the raw list of the players.
     * @return The player list.
     */
    List<CardPlayer> getPlayers();

    /**
     * Get the max player number of the list.
     * @return The max player number.
     */
    int getMaxPlayers();

    /**
     * Set the max player of the list.
     * @param maxPlayers The max player of the list.
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * Add a player to the player list.
     * @param player The added player.
     * @return If the operation succeed.
     */
    ApplicationResult addPlayer(CardPlayer player);

    /**
     * Remove a player from the player list.
     * @param player The removed player.
     * @return If the operation succeed.
     */
    ApplicationResult removePlayer(CardPlayer player);

    /**
     * Remove a player by its controller.
     * @param cardPlayer The controller of the removed player.
     * @return If the operation succeed.
     */
    ApplicationResult removePlayer(PlayerController cardPlayer);

    /**
     * Get the player who is on the big blind position.
     * @return The CardPlayer on the big blind position.
     */
    CardPlayer getBigBlindPlayer();

    /**
     * Get the player who is on the small blind position.
     * @return The CardPlayer on the small blind position.
     */
    CardPlayer getSmallBlindPlayer();

    /**
     * Get an iterator starting on the first player in the player list.
     * @return An iterator with start index 0.
     */
    PlayerIterator getIterator();

    /**
     * Get an iterator starting on the appointed position.
     * The iterator is single-round, and will stop at the position ahead of the start.
     * @param position The appointed start position.
     * @return The required iterator with start position on the appointed one.
     */
    PlayerIterator getIterator(int position);

    /**
     * Get the iterator starting on the next position of the button.
     * The iterator is single-round, and will stop at the position ahead of the start.
     * This will be frequently used, like every small round start with the small blind player.
     * @return The required iterator with start position on the next position of the button.
     */
    PlayerIterator getIteratorFromAfterButton();

    /**
     * Get a loop iterator starting with the appointed position.
     * This is a loop iterator, which means it will not stop.
     * When reaching the rear, next() will advance to the start.
     * @param position The appointed starting position.
     * @return The required iterator with start position on the appointed one.
     */
    PlayerIterator getLoopIterator(int position);
}
