package control.gameplayer;

import control.playercontroller.PlayerController;
import control.playeridentifier.PlayerIdentifier;

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
    PlayerController getPlayerController();
    PlayerIdentifier getPlayerIdentifier();
}
