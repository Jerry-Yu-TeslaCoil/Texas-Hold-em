package control.impl;

import control.GamePlayer;
import control.vo.PlayerPersonalVO;
import lombok.extern.log4j.Log4j2;
import table.card.PokerCard;
import table.mechanism.DecisionRequest;
import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;
import table.vo.privateinfo.PlayerPrivateVO;
import table.vo.publicinfo.PublicVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple robot simulation. Only do CALL in the round.
 *
 * <p>
 *     This is considered only a test class for the robot simulation.
 *     It only operates simple decisions.
 *     No actual player or AI involved.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Log4j2
public class RobotGamePlayerCallOnly implements GamePlayer {

    private final ArrayList<PokerCard> pokerCards;

    @Override
    public void updatePublicInfo(PublicVO publicVO) {
        log.trace("Updating public info: {}", publicVO);
    }

    @Override
    public void updatePrivateInfo(PlayerPrivateVO privateInfoVO) {
        log.trace("Updating private info: {}", privateInfoVO);
        this.pokerCards.addAll(List.of(privateInfoVO.holeCards()));
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        log.trace("PCPlayerControl: I have Cards: {}", pokerCards);
        log.info("PC Player decided to CALL.");
        return () -> DecisionType.CALL;
    }

    /**
     * Construct a simple robot controller.
     */
    public RobotGamePlayerCallOnly() {
        this.pokerCards = new ArrayList<>();
    }

    @Override
    public PlayerPersonalVO getPlayerPersonalVO() {
        return new PlayerPersonalVO();
    }
}
