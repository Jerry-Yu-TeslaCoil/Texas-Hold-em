package table.state.gamestate.impl;

import table.card.PokerCard;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;

public enum FlopStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {

        //Take the first three cards
        PokerCard[] publicCards = context.getPublicCards();
        context.getCardDeck().shuffle();
        publicCards[0] = context.getCardDeck().takePeekCard();
        context.getCardDeck().shuffle();
        publicCards[1] = context.getCardDeck().takePeekCard();
        context.getCardDeck().shuffle();
        publicCards[2] = context.getCardDeck().takePeekCard();

       //TODO: Finish betting rounds
        return GameState.FLOP;
    }
}
