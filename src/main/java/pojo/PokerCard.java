package pojo;

public interface PokerCard {
    Suit getSuit();
    Rank getRank();

    PokerCard copy();
}
