import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.card.CardDeck;
import table.card.CardDeckFactory;
import table.card.impl.NoJokerDeckFactory;

@Log4j2
public class TestPokerDeck {

    @Test
    public void testGenerateDeck() {
        CardDeckFactory factory = NoJokerDeckFactory.getInstance();
        CardDeck cardDeck = factory.getCardDeck();
        log.info(cardDeck);
    }

    @Test
    public void testShuffleDeck() {
        CardDeckFactory factory = NoJokerDeckFactory.getInstance();
        CardDeck cardDeck = factory.getCardDeck();
        cardDeck.shuffle();
        log.info(cardDeck);
    }

    @Test
    public void testDeckGetPeek() {
        CardDeckFactory factory = NoJokerDeckFactory.getInstance();
        CardDeck cardDeck = factory.getCardDeck();
        cardDeck.shuffle();
        log.info(cardDeck.takePeekCard());
    }
}
