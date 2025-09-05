package table.card;

/**
 * Deck of poker card
 *
 * <p>
 *     As a card deck, it is used in game progress to generate, shuffle and provide poker cards.
 *     For example, Texas Hold'em uses a poker set without two Jokers.
 * </p>
 *
 * <p>
 *     This is usually used temporarily and disposable, and to be constructed by CardDeckFactory.
 *     Currently, this is not thread-safe.
 *     After several versions, it may be developed to be thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface CardDeck {

    /**
     * Get the number of cards in the deck.
     * @return Number of poker cards.
     */
    int getCardNum();

    /**
     * Shuffle the poker deck.
     */
    void shuffle();

    /**
     * Add a card to the deck.
     * @param card The card needed to be added.
     */
    void addCard(PokerCard card);

    /**
     * Take a card from the peek of the card deck.
     * @return The card on the peek.
     */
    PokerCard takePeekCard();

    /**
     * Deep copy the card deck.
     * @return A copy of the current deck.
     */
    CardDeck copy();
}
