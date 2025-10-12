import control.PlayerController;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import control.impl.RobotPlayerCallOnly;
import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.impl.PlayerCoil;
import table.player.impl.SimpleCardPlayer;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

@Log4j2
public class TestPlayerCoil {

    @Test
    public void testIndexRound() {
        int index = -7;
        int round = 5;
        log.info(index % round + round);
    }

    @Test
    public void testPlayerCoilIterator() {
        PlayerCoil playerCoil = new PlayerCoil();
        for (int i = 0; i < 20; i++) {
            playerCoil.addPlayer(new SimpleCardPlayer(new RobotPlayerCallOnly(), BigDecimal.ONE, i));
        }
        log.info(playerCoil);
        Iterator<CardPlayer> playerCoilIterator = playerCoil.getIterator();
        while (playerCoilIterator.hasNext()) {
            CardPlayer cardPlayer = playerCoilIterator.next();
            log.info(cardPlayer);
        }
    }

    @Test
    public void testPlayerCoilIteratorWithListOperation() {
        PlayerCoil playerCoil = new PlayerCoil();
        for (int i = 0; i < 20; i++) {
            playerCoil.addPlayer(new SimpleCardPlayer(new RobotPlayerCallOnly(), BigDecimal.ONE, i));
        }
        log.info(playerCoil);
        PlayerIterator playerIterator = playerCoil.getIterator();
        while (playerIterator.hasNext()) {
            CardPlayer cardPlayer = null;
            try {
                cardPlayer = playerIterator.next();
            } catch (ConcurrentModificationException e) {
                log.info(e.getMessage());
                int curr = playerIterator.getCurrentPosition();
                int modified = 2;
                playerIterator = playerCoil.getIterator();
                playerIterator.jumpTo(curr + modified);
            }
            log.info(cardPlayer);
            playerCoil.removePlayer(cardPlayer);
        }
        log.info(playerCoil);
    }

    @Test
    public void testPlayerCoilRemoveByController() {
        PlayerCoil playerCoil = new PlayerCoil();
        PlayerController playerController = new RobotPlayerCallOnly();
        for (int i = 0; i < 20; i++) {
            if (i == 5) {
                playerCoil.addPlayer(new SimpleCardPlayer(playerController, BigDecimal.ONE, i));
            } else {
                playerCoil.addPlayer(new SimpleCardPlayer(new RobotPlayerCallOnly(), BigDecimal.ONE, i));
            }
        }
        log.info(playerCoil);
        playerCoil.removePlayer(playerController);
        log.info(playerCoil);
    }
}
