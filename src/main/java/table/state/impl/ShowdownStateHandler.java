package table.state.impl;

import lombok.extern.log4j.Log4j2;
import table.pot.PlayerRanking;
import table.pot.PotManager;
import table.state.GameState;
import table.state.GameStateContext;
import table.state.GameStateHandler;
import util.PlayerUtil;

import java.util.PriorityQueue;

@Log4j2
public enum ShowdownStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("Enter Showdown State");

        context.getTablePublicVOBuilder().setCurrentGameState(GameState.SHOWDOWN);
        context.getPlayerPublicVOBuilder().setState(GameState.SHOWDOWN);

        PlayerUtil.flopAllCards(context);
        PotManager manager = context.getPotManager();
        PriorityQueue<PlayerRanking> playerRankings;
        playerRankings = PlayerUtil.getPlayerRankings(context);
        manager.judge(playerRankings);
        context.setPotJudged(true);

        context.getTablePublicVOBuilder().setPublicCards(context.getPublicCards());
        PlayerUtil.buildAndPublishVO(context);
        return GameState.END;
    }


}
