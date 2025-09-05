package table.card.impl;

import exception.IllegalCardException;
import table.card.PokerCard;
import table.card.Rank;
import table.card.Suit;

/**
 * A plain poker card. The simplest form of card.
 *
 * <p>
 *     The suit and the rank are fixed as final variable.
 *     That means no changes could occur to the card.
 *     It can be used under most circumstances, but not suitable when the suit or card could be changed.
 * </p>
 *
 * <p>
 *     Pinned suit and rank also means this is suitable for flyweight patterns.
 *     Do construct it with a flyweight factory if needed, but if so, <b>do not use copy function</b>.
 *     It will create a new card.
 * </p>
 *
 * @param suit The suit of the card.
 * @param rank The rank of the card.
 *
 * @author jerry
 *
 * @version 1.0
 */
public record FixedPokerCard(Suit suit, Rank rank) implements PokerCard {

    /**
     * Construct a card with appointed suit and rank.
     * @param suit The suit of the card.
     * @param rank The rank of the card.
     */
    public FixedPokerCard {
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
    }

    /**
     * Copy a <b>new card instance</b> with the same suit and rank as the current card.
     * @return A new copied card.
     */
    @Override
    public PokerCard copy() {
        return new FixedPokerCard(this.suit, this.rank);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FixedPokerCard other = (FixedPokerCard) obj;
        return suit == other.suit && rank == other.rank;
    }

    @Override
    public String toString() {
        if (suit == Suit.jokers) {
            return rank.toString();
        }
        return rank + "_OF_" + suit;
    }
}
