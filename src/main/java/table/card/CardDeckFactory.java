package table.card;

/**
 * Factory of card deck construction.
 *
 * <p>
 *     This factory is used for generating a card deck.
 *     Using different factory subclasses, different sorts of deck can be constructed.
 *     For example, Texas Hold'em requires a poker set without Jokers.
 *     But other games or novel rules may need it.
 * </p>
 *
 * <p>
 *     This factory is thread-safe as it contains no public stateful data.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface CardDeckFactory {

    /**
     * Generate and return a card deck.
     * @return Created card deck.
     */
    public CardDeck getCardDeck();
}
