package pojo.impl;

import pojo.CardDeck;
import pojo.CardDeckFactory;
import pojo.Rank;
import pojo.Suit;

public class NoJokerDeckFactory implements CardDeckFactory {
    private static final CardDeck CACHED_DECK = createDeck();

    private static CardDeck createDeck() {
        CardDeck deck = new CardDeckImpl();
        for (Suit suit : Suit.values()) {
            if (suit == Suit.jokers) continue;
            for (Rank rank : Rank.values()) {
                if (rank == Rank.BLACK_JOKER || rank == Rank.RED_JOKER) continue;
                deck.addCard(new PokerCardImpl(suit, rank));
            }
        }
        return deck;
    }

    @Override
    public CardDeck getCardDeck() {
        return CACHED_DECK.copy();
    }
}