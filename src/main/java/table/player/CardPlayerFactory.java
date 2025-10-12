package table.player;

import control.PlayerController;
import table.config.TableConfig;

/**
 * Factory class constructing a CardPlayer with TableConfig.
 *
 * <p>
 *     The factory is used for table to create and init CardPlayer with the PlayerController.
 *     With it, a "participant player" can be created on the Table.
 *     Also, factory will automatically allocate an index for the CardPlayer.
 * </p>
 *
 * <p>
 *     The current version is not thread-safe due to the index allocation.
 *     In the future edition, this should be fixed.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface CardPlayerFactory {

    /**
     * Set the factory's config.
     * @param config Config of the table.
     */
    void setConfig(TableConfig config);

    /**
     * Create a CardPlayer with the controller, the config and an id.
     * @param controller The controller of the CardPlayer.
     * @return A CardPlayer with the given controller.
     */
    CardPlayer createCardPlayer(PlayerController controller);

    /**
     * Reset factory id for CardPlayers to starting from 0.
     */
    void resetId();
}
