package table.state.gamestate.impl;

import table.card.CardDeck;
import table.card.PokerCard;
import table.card.impl.NoJokerDeckFactory;
import table.player.PlayerList;
import table.pot.PotManager;
import table.state.gamestate.GameState;
import table.state.gamestate.GameStateContext;
import table.state.gamestate.GameStateHandler;

/**
 * Handler enum of the init state.
 *
 * <p>
 *     This state handler is used for initialization of the round.
 *     For example, double check if the pot manager is cleared for the next game, or create a deck of cards.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public enum InitStateHandler implements GameStateHandler {
    INSTANCE;

    @Override
    public GameState execute(GameStateContext context) {
        PlayerList players = context.getPlayers();
        if (players == null) {
            throw new RuntimeException("Player list is null");
        }
        //TODO: Lock thread to avoid more player joining the game
        PotManager potManager = context.getPotManager();
        if (potManager == null) {
            throw new RuntimeException("Pot manager is null");
        }
        potManager.clearStack();
        CardDeck cardDeck = NoJokerDeckFactory.getInstance().getCardDeck();
        cardDeck.shuffle();
        context.setCardDeck(cardDeck);
        if (context.getTableConfig() == null) {
            throw new RuntimeException("Table config is null");
        }
        context.setPublicCards(new PokerCard[5]);
        //TODO: Show info to players
        return GameState.PRE_FLOP;
    }
}