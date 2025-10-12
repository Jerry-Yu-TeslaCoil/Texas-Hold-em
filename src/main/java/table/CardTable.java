package table;

import control.GamePlayer;
import table.config.TableConfig;
import util.ApplicationResult;

/**
 * A table class, representing a game table.
 *
 * <p>
 *     This is the outer shell of the game logic.
 *     It serves as a field organizing PlayerControllers to play a game,
 *     meanwhile, manage the player to join or leave.
 *     The table's config can be set, and the table will automatically process it.
 * </p>
 *
 * <p>
 *     At the start of the game, every player is allocated with the same amount of chips.
 *     Call startRounds() to start a series of rounds.
 *     After certain rounds, players will be ranked with the rest of their chips.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface CardTable {

    /**
     * Set the table's config.
     * @param tableConfig The table's config. Containing various settings like the max player num and initial chips.
     */
    void setTableConfig(TableConfig tableConfig);

    /**
     * Get the table's config.
     * @return The table's config.
     */
    TableConfig getTableConfig();

    /**
     * Make a player to join the table.
     * The player is actually a PlayerController.
     * @param cardPlayer The player controller.
     * @return An ApplicationResult that includes result and a failure message.
     */
    ApplicationResult playerJoin(GamePlayer cardPlayer);

    /**
     * Remove a player from the table.
     * @param cardPlayer The removed playerController.
     * @return An ApplicationResult that includes result and a failure message.
     */
    ApplicationResult playerLeave(GamePlayer cardPlayer);

    /**
     * Start the game. Keep continuing before the game ends or lack of players.
     */
    void startRounds();
}
