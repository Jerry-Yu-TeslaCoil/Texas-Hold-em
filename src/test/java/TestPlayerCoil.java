import org.junit.Test;
import table.control.impl.PCPlayerControl;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.impl.SimpleCardPlayer;
import table.player.impl.PlayerCoil;

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
            playerCoil.addPlayer(new SimpleCardPlayer(new PCPlayerControl(), BigDecimal.ONE));
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
            playerCoil.addPlayer(new SimpleCardPlayer(new PCPlayerControl(), BigDecimal.ONE));
        }
        System.out.println(playerCoil);
        PlayerIterator playerIterator = playerCoil.getIterator();
        while (playerIterator.hasNext()) {
            CardPlayer cardPlayer = null;
            try {
                cardPlayer = playerIterator.next();
            } catch (ConcurrentModificationException e) {
                System.out.println(e.getMessage());
                int curr = playerIterator.getCurrentPosition();
                int modified = 2;
                playerIterator = playerCoil.getIterator();
                playerIterator.jumpTo(curr + modified);
            }
            System.out.println(cardPlayer);
            playerCoil.removePlayer(cardPlayer);
        }
        System.out.println(playerCoil);
    }
}
