package table.state.gamestate.impl;

import table.card.PokerCard;
import table.mechanism.DecisionRequest;
import table.mechanism.DecisionType;
import table.mechanism.ResolvedAction;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.PlayerList;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;

import java.math.BigDecimal;

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
        BigDecimal currentBet = BigDecimal.ZERO;
        PlayerList players = context.getPlayers();
        int pointer = players.getButtonPosition();
        PlayerIterator iterator = players.getIterator(pointer);
        while (iterator.hasNext()) {
            CardPlayer next = iterator.next();
            ResolvedAction playerDecision = next.getPlayerDecision(new DecisionRequest(currentBet));
            if (playerDecision.decisionType() == DecisionType.RAISE) {
                iterator = players.getIterator(iterator.getCurrentPosition() + 1);
            }
            //TODO: Refresh players' view
        }
        return GameState.FLOP;
    }
}
