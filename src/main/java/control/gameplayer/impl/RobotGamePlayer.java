package control.gameplayer.impl;

import control.gameplayer.GamePlayer;
import control.playercontroller.PlayerController;
import control.playeridentifier.PlayerIdentifier;
import control.playeridentifier.impl.RobotIdentifier;

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
