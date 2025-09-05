package table.card;

public interface CardDeck {
    int getCardNum();
    void shuffle();
    void addCard(PokerCard card);
    PokerCard takePeekCard();

    CardDeck copy();
}
