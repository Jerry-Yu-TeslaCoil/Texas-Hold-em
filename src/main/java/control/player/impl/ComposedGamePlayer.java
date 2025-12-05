package control.player.impl;

import control.player.GamePlayer;
import control.player.controller.PlayerController;
import control.player.identifier.PlayerIdentifier;

/**
 * The composition form of a GamePlayer.
 *
 * <p>
 * This is the most generic form.
 * It takes corresponding args and makes it final as a record class.
 * </p>
 *
 * <p>
 *     This is considered thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
public record ComposedGamePlayer(PlayerController playerController,
                                 PlayerIdentifier<?> playerIdentifier) implements GamePlayer {

}
