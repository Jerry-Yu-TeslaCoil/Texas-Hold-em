package control.impl;

import control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;
import table.mechanism.impl.CallDecision;
import table.mechanism.impl.FoldDecision;
import table.mechanism.impl.RaiseDecision;
import table.vo.privateinfo.PlayerGamePrivateInfoVO;
import table.vo.publicinfo.PublicInfoVO;

import java.math.BigDecimal;

/**
 * A robot player controller that only react as what it is set.
 *
 * <p>
 *     This is used for controllable testing.
 *     By preset the DecisionType, the robot will only react to decision by appointed type.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class RobotPlayerDoPreset implements PlayerController {
    private DecisionType presetDecisionType = DecisionType.CALL;
    private BigDecimal decisionValue = BigDecimal.ONE;

    /**
     * Set the robot's reaction.
     * @param presetDecisionType The type of the decision.
     * @param decisionValue The value of the decision. Only used for RAISE currently.
     */
    public void setReaction(DecisionType presetDecisionType, BigDecimal decisionValue) {
        this.presetDecisionType = presetDecisionType;
        this.decisionValue = decisionValue;
    }

    @Override
    public void updatePublicInfo(PublicInfoVO publicInfoVO) {
        //It doesn't care. Do nothing.
    }

    @Override
    public void updatePrivateInfo(PlayerGamePrivateInfoVO privateInfoVO) {
        //It doesn't care. Do nothing.
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        return switch (this.presetDecisionType) {
            case FOLD -> new FoldDecision();
            case CALL -> new CallDecision();
            case RAISE -> new RaiseDecision(this.decisionValue);
        };
    }
}
