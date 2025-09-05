package table.card.impl;

import table.card.CardDeck;
import table.card.PokerCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Simple form of card deck which picks cards from the peek, and inserts cards to the bottom.
 *
 * <p>
 *     Handle for most circumstances in the Texas Hold'em. Used to serve mechanism of the whole game.
 * </p>
 *
 * <p>
 *     <b>This is not thread-safe.</b>
 *     After several versions, it may be developed tread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class SimpleCardDeck implements CardDeck {

    private final ArrayList<PokerCard> cards;

    /**
     * Create a card deck with no card initially.
     */
    public SimpleCardDeck() {
        cards = new ArrayList<>();
    }

    @Override
    public int getCardNum() {
        return cards.size();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    @Override
    public void addCard(PokerCard card) {
        this.cards.add(card);
    }

    @Override
    public PokerCard takePeekCard() {
        PokerCard pokerCard = cards.get(0);
        this.cards.remove(0);
        return pokerCard;
    }

    @Override
    public CardDeck copy() {
        SimpleCardDeck cardDeck = new SimpleCardDeck();
        for (PokerCard card : cards) {
            cardDeck.addCard(card.copy());
        }
        return cardDeck;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (PokerCard card : cards) {
            builder.append(card.toString());
            builder.append(" | ");
        }
        return "CardDeck " + hashCode() + " with total cards " + cards.size() + " including [" + builder + "]";
    }
}
