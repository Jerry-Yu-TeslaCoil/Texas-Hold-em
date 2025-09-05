import org.junit.Test;
import table.card.CardDeck;
import table.card.impl.NoJokerDeckFactory;

public class TestPokerDeck {

    @Test
    public void testGenerateDeck() {
        NoJokerDeckFactory factory = new NoJokerDeckFactory();
        CardDeck cardDeck = factory.getCardDeck();
        System.out.println(cardDeck);
    }

    @Test
    public void testShuffleDeck() {
        NoJokerDeckFactory factory = new NoJokerDeckFactory();
        CardDeck cardDeck = factory.getCardDeck();
        cardDeck.shuffle();
        System.out.println(cardDeck);
    }

    @Test
    public void testDeckGetPeek() {
        NoJokerDeckFactory factory = new NoJokerDeckFactory();
        CardDeck cardDeck = factory.getCardDeck();
        cardDeck.shuffle();
        System.out.println(cardDeck.takePeekCard());
    }
}
