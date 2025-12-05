package table.state.impl;

import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.state.GameState;
import table.state.GameStateContext;
import table.state.GameStateHandler;
import util.PlayerUtil;

@Log4j2
public enum FlopStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        log.info("Enter Flop State");
        context.getTablePublicVOBuilder().setCurrentGameState(GameState.FLOP);
        context.getPlayerPublicVOBuilder().setState(GameState.FLOP);
        sendPublicCards(context);

        return PlayerUtil.startBet(context, GameState.TURN);
    }

    private static void sendPublicCards(GameStateContext context) {
        PokerCard[] publicCards = context.getPublicCards();
        context.getCardDeck().shuffle();
        publicCards[0] = context.getCardDeck().takePeekCard();
        context.getCardDeck().shuffle();
        publicCards[1] = context.getCardDeck().takePeekCard();
        context.getCardDeck().shuffle();
        publicCards[2] = context.getCardDeck().takePeekCard();
        context.getTablePublicVOBuilder().setPublicCards(publicCards);
    }
}
