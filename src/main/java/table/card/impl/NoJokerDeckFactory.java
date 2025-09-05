package table.card.impl;

import table.card.*;

/**
 * Card deck factory that generate a card deck with the whole set except two jokers.
 *
 * <p>
 *     Game objects.
 *     Serving the mechanism and used when a new deck is needed.
 *     Initially, the order would be:<br>
 *     - {clubs, diamonds, hearts, spades}<br>
 *     - {A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K}
 * </p>
 *
 * <p>
 *     This factory is <b>thread-safe</b>.
 *     It uses a cached set of card deck and its copy function to construct a new set of card deck.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class NoJokerDeckFactory implements CardDeckFactory {
    private static final CardDeck CACHED_DECK = createDeck();

    private static CardDeck createDeck() {
        CardDeck deck = new SimpleCardDeck();
        for (Suit suit : Suit.values()) {
            if (suit == Suit.jokers) continue;
            for (Rank rank : Rank.values()) {
                if (rank == Rank.BLACK_JOKER || rank == Rank.RED_JOKER) continue;
                deck.addCard(new FixedPokerCard(suit, rank));
            }
        }
        return deck;
    }

    /**
     * Get the product poker deck of the factory.<br>
     * The generation of card deck copies deeply to the card.
     * So cards in the deck are disposable, and would not influence other decks.
     */
    @Override
    public CardDeck getCardDeck() {
        return CACHED_DECK.copy();
    }
}