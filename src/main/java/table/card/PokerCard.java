package table.card;

/**
 * Poker card representation
 *
 * <p>
 *     Any subclass containing a suit, a rank and the ability of self-copy can be a poker card.
 *     This is used throughout the game, serving the mechanism of poker.
 * </p>
 *
 * <p>
 *     May use flyweight pattern to optimize the requirements of memory in subclasses.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PokerCard {

    /**
     * Get the suit of this card.
     * @return The suit of the card
     */
    Suit suit();

    /**
     * Get the rank of this card.
     * @return The rank of the card
     */
    Rank rank();

    /**
     * Self copy.
     * @return The copy of this card.
     */
    PokerCard copy();
}
