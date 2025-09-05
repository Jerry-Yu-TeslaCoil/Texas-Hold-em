package table.card;

public interface PokerCard {
    Suit getSuit();
    Rank getRank();

    PokerCard copy();
}
