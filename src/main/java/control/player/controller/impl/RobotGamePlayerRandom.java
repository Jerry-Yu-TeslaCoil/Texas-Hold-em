package control.player.controller.impl;

import control.player.controller.PlayerController;
import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.rule.decision.DecisionRequest;
import table.rule.decision.PlayerDecision;
import table.rule.decision.impl.CallDecision;
import table.rule.decision.impl.FoldDecision;
import table.rule.decision.impl.RaiseDecision;
import table.vo.privateinfo.PlayerPrivateVO;
import table.vo.publicinfo.PublicVO;

import java.math.BigDecimal;
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
public class RobotGamePlayerRandom implements PlayerController {

    private final PokerCard[] pokerCards = new PokerCard[2];
    private final Random rand = new Random();

    public RobotGamePlayerRandom() {
        this.rand.setSeed(System.currentTimeMillis());
    }

    @Override
    public void updatePublicInfo(PublicVO publicVO) {
    }

    @Override
    public void updatePrivateInfo(PlayerPrivateVO privateInfoVO) {
        log.trace("Updating private info: {}", privateInfoVO);
        this.pokerCards[0] = privateInfoVO.getHoleCards()[0];
        this.pokerCards[1] = privateInfoVO.getHoleCards()[1];
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        log.trace("The player has {}", (Object) pokerCards);
        int decision = rand.nextInt(10);
        if (decision < 3) {
            log.info("PC Player decided to Raise.");
            return new RaiseDecision(BigDecimal.ONE);
        } else if (decision < 7) {
            log.info("PC Player decided to Call.");
            return new CallDecision();
        } else {
            log.info("PC Player decided to Fold.");
            return new FoldDecision();
        }
    }
}
