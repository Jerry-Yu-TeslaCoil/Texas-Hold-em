package table.state.gamestate.impl;

import lombok.extern.log4j.Log4j2;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.PlayerList;
import table.pot.PlayerRanking;
import table.pot.PotManager;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;
import util.PlayerUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.PriorityQueue;

@Log4j2
public enum EndStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("Enter End State");

        context.getTablePublicVOBuilder().setCurrentGameState(GameState.END);
        context.getPlayerPublicVOBuilder().setState(GameState.END);

        PlayerUtil.flopAllCards(context);

        PotManager potManager = context.getPotManager();

        if (!context.isPotJudged()) {
            PriorityQueue<PlayerRanking> playerRankings;
            playerRankings = PlayerUtil.getPlayerRankings(context);
            potManager.judge(playerRankings);
        }
        settlePrize(context, potManager);

        PlayerUtil.buildAndPublishVO(context);

        return GameState.INIT;
    }

    private static void settlePrize(GameStateContext context, PotManager potManager) {
        HashMap<CardPlayer, BigDecimal> playerPrizeStack = potManager.getPlayerPrizeStack();
        PlayerList players = context.getPlayers();
        PlayerIterator iterator = players.getIterator();
        while (iterator.hasNext()) {
            CardPlayer player = iterator.next();
            player.setPlayerPrize(playerPrizeStack.getOrDefault(player, BigDecimal.ZERO));
            player.setStack(player.getStack().add(player.getPlayerPrize()));
        }
    }
}
