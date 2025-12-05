package control.player.impl;

import control.player.GamePlayer;
import control.player.controller.PlayerController;
import control.player.identifier.PlayerIdentifier;

/**
 * The composition form of a GamePlayer.
 */
public class ComposedGamePlayer implements GamePlayer {

    private final PlayerController playerController;
    private final PlayerIdentifier playerIdentifier;

    public ComposedGamePlayer(PlayerController playerController, PlayerIdentifier playerIdentifier) {
        this.playerController = playerController;
        this.playerIdentifier = playerIdentifier;
    }

    @Override
    public PlayerController getPlayerController() {
        return this.playerController;
    }

    @Override
    public PlayerIdentifier getPlayerIdentifier() {
        return this.playerIdentifier;
    }
}
