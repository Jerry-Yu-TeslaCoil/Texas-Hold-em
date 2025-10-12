package control.impl;

import control.vo.PlayerPersonalVO;
import table.card.PokerCard;
import control.GamePlayer;
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
public class RobotGamePlayerCallOnly implements GamePlayer {

    private final ArrayList<PokerCard> pokerCards;

    @Override
    public void updatePublicInfo(PublicVO publicVO) {
        //It doesn't care. Do nothing.
    }

    @Override
    public void updatePrivateInfo(PlayerPrivateVO privateInfoVO) {
        this.pokerCards.addAll(List.of(privateInfoVO.holeCards()));
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        System.out.println("PC Player decided to CALL.");
        System.out.println("PCPlayerControl: I have Cards: " + pokerCards);
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
