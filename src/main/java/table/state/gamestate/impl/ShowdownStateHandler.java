package table.state.gamestate.impl;

import lombok.extern.log4j.Log4j2;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;
import util.PlayerUtil;

@Log4j2
public enum ShowdownStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("Enter Showdown State");

        context.getTablePublicVOBuilder().setCurrentGameState(GameState.SHOWDOWN);
        context.getPlayerPublicVOBuilder().setState(GameState.SHOWDOWN);

        PlayerUtil.flopAllCards(context);

        PlayerUtil.buildAndPublishVO(context);
        return GameState.END;
    }


}
