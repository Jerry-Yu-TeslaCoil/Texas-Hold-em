package control.player.impl;

import control.player.GamePlayer;
import control.player.controller.PlayerController;
import control.player.identifier.PlayerIdentifier;
import control.player.identifier.impl.RobotIdentifier;

/**
 * A robot GamePlayer, with default robot identifier.
 *
 * <p>
 *     This is used for an easier testing robot construction.
 * </p>
 *
 * @author jerry
 *
 * @version 1.1
 */
public class RobotGamePlayer implements GamePlayer {

    private final PlayerIdentifier<?> identifier;
    private final PlayerController controller;

    public RobotGamePlayer(PlayerController controller) {
        this.controller = controller;
        this.identifier = new RobotIdentifier();
    }

    @Override
    public PlayerController playerController() {
        return controller;
    }

    @Override
    public PlayerIdentifier<?> playerIdentifier() {
        return identifier;
    }
}
