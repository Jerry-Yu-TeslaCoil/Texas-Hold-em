package control.player.impl;

import control.player.GamePlayer;
import control.player.controller.PlayerController;
import control.player.identifier.PlayerIdentifier;
import control.player.identifier.impl.RobotIdentifier;

public class RobotGamePlayer implements GamePlayer {

    private final PlayerIdentifier identifier;
    private final PlayerController controller;

    public RobotGamePlayer(PlayerController controller) {
        this.controller = controller;
        this.identifier = new RobotIdentifier();
    }

    @Override
    public PlayerController getPlayerController() {
        return controller;
    }

    @Override
    public PlayerIdentifier getPlayerIdentifier() {
        return identifier;
    }
}
