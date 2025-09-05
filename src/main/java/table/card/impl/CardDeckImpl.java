package table.card.impl;

import table.card.CardDeck;
import table.card.PokerCard;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeckImpl implements CardDeck {

    private final ArrayList<PokerCard> cards;

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
        CardDeckImpl cardDeck = new CardDeckImpl();
        for (PokerCard card : cards) {
            cardDeck.addCard(card.copy());
        }
        return cardDeck;
    }

    public CardDeckImpl() {
        cards = new ArrayList<>();
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
