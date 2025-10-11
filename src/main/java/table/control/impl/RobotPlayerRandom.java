package table.control.impl;

import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.PlayerDecision;
import table.mechanism.impl.CallDecision;
import table.mechanism.impl.FoldDecision;
import table.mechanism.impl.RaiseDecision;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;

/**
 * A robot player making decision randomly.
 *
 * <p>
 *     This is used for tests that are more complex.
 *     For 10%, the robot will raise 1 chip.
 *     For 70%, the robot will call.
 *     For 20%, the robot will fold.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Log4j2
public class RobotPlayerRandom implements PlayerController {

    private final PokerCard[] pokerCards = new PokerCard[2];
    private int pokerCardIndex = 0;

    @Override
    public void addHoleCard(PokerCard pokerCard) {
        pokerCards[pokerCardIndex++] = pokerCard;
        log.info("Current poker card: {}", Arrays.toString(pokerCards));
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int decision = rand.nextInt(10);
        if (decision == 0) {
            log.info("PC Player decided to Raise.");
            return new RaiseDecision(BigDecimal.ONE);
        } else if (decision <= 7) {
            log.info("PC Player decided to Call.");
            return new CallDecision();
        } else {
            log.info("PC Player decided to Fold.");
            return new FoldDecision();
        }
    }
}
