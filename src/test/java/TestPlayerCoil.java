import org.junit.Test;
import pojo.CardPlayer;
import pojo.PlayerCoil;
import pojo.impl.CardPlayerImpl;
import pojo.impl.PCPlayerControl;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class TestPlayerCoil {
    @Test
    public void testIndexRound() {
        int index = -7;
        int round = 5;
        System.out.println(index % round + round);
    }

    @Test
    public void testPlayerCoilIterator() {
        PlayerCoil playerCoil = new PlayerCoil();
        for (int i = 0; i < 20; i++) {
            playerCoil.addPlayer(new CardPlayerImpl(new PCPlayerControl(), BigDecimal.ONE));
        }
        System.out.println(playerCoil);
        Iterator<CardPlayer> playerCoilIterator = playerCoil.getIterator();
        while (playerCoilIterator.hasNext()) {
            CardPlayer cardPlayer = playerCoilIterator.next();
            System.out.println(cardPlayer);
        }
    }

    @Test
    public void testPlayerCoilIteratorWithListOperation() {
        PlayerCoil playerCoil = new PlayerCoil();
        for (int i = 0; i < 20; i++) {
            playerCoil.addPlayer(new CardPlayerImpl(new PCPlayerControl(), BigDecimal.ONE));
        }
        System.out.println(playerCoil);
        PlayerCoil.CoilIterator playerCoilIterator = playerCoil.getIterator();
        while (playerCoilIterator.hasNext()) {
            CardPlayer cardPlayer = null;
            try {
                cardPlayer = playerCoilIterator.next();
            } catch (ConcurrentModificationException e) {
                int curr = playerCoilIterator.getCurrentPosition();
                int modified = playerCoilIterator.getModifiedPlayerNum();
                playerCoilIterator = playerCoil.getIterator();
                playerCoilIterator.jumpTo(curr + modified);
            }
            System.out.println(cardPlayer);
            playerCoil.removePlayer(cardPlayer);
        }
        System.out.println(playerCoil);
    }
}
