package control.player;

import control.player.controller.PlayerController;
import control.player.identifier.PlayerIdentifier;

/**
 * Game player class.
 *
 * <p>
 *     A GamePlayer has the ability both identify oneself and react to the game process.
 *     Thus, this class is used for the game ultimately.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface GamePlayer {

    /**
     * Getter of the controller
     * @return The controller of the GamePlayer
     */
    PlayerController playerController();

    /**
     * Getter of the identifier
     * @return The identifier of the GamePlayer
     */
    PlayerIdentifier<?> playerIdentifier();
}
