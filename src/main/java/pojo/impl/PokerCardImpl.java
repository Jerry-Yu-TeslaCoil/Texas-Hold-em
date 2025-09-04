package pojo.impl;

import pojo.PokerCard;
import pojo.Rank;
import pojo.Suit;
import pojo.exception.IllegalCardException;

public class PokerCardImpl implements PokerCard {

    private final Suit suit;
    private final Rank rank;

    public PokerCardImpl(Suit suit, Rank rank) {
    if (suit == null) {
        throw new NullPointerException("Suit cannot be null");
    }
    if (rank == null) {
        throw new NullPointerException("Rank cannot be null");
    }
    if (suit == Suit.jokers && !(rank == Rank.RED_JOKER || rank == Rank.BLACK_JOKER)) {
        throw new IllegalCardException(
            String.format("Invalid card: suit '%s' can only be used with RED_JOKER or BLACK_JOKER rank, but got '%s'",
                         suit, rank)
        );
    }
    if ((rank == Rank.RED_JOKER || rank == Rank.BLACK_JOKER) && suit != Suit.jokers) {
        throw new IllegalCardException(
            String.format("Invalid card: rank '%s' can only be used with jokers suit, but got '%s'",
                         rank, suit)
        );
    }
    this.suit = suit;
    this.rank = rank;
}

    @Override
    public Suit getSuit() {
        return this.suit;
    }

    @Override
    public Rank getRank() {
        return this.rank;
    }

    @Override
    public PokerCard copy() {
        return new PokerCardImpl(this.suit, this.rank);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PokerCardImpl other = (PokerCardImpl) obj;
        return suit == other.suit && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return 31 * suit.hashCode() + rank.hashCode();
    }

    @Override
    public String toString() {
        if (suit == Suit.jokers) {
            return rank.toString();
        }
        return rank + "_OF_" + suit;
    }
}
