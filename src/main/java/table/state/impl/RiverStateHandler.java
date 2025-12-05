package table.state.impl;

import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.state.GameState;
import table.state.GameStateContext;
import table.state.GameStateHandler;
import util.PlayerUtil;

@Log4j2
public enum RiverStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("Enter River State");

        context.getTablePublicVOBuilder().setCurrentGameState(GameState.RIVER);
        context.getPlayerPublicVOBuilder().setState(GameState.RIVER);

        flopCard(context);

        return PlayerUtil.startBet(context, GameState.SHOWDOWN);
    }

    private static void flopCard(GameStateContext context) {
        context.getCardDeck().shuffle();
        PokerCard pokerCard = context.getCardDeck().takePeekCard();
        context.getPublicCards()[4] = pokerCard;
        context.getTablePublicVOBuilder().setPublicCards(context.getPublicCards());
        PlayerUtil.buildAndPublishVO(context);
    }
}
