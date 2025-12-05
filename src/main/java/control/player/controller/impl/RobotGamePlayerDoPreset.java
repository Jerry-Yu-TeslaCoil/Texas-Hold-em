package control.player.controller.impl;

import control.player.controller.PlayerController;
import lombok.extern.log4j.Log4j2;
import table.rule.decision.DecisionRequest;
import table.rule.decision.DecisionType;
import table.rule.decision.PlayerDecision;
import table.rule.decision.impl.CallDecision;
import table.rule.decision.impl.FoldDecision;
import table.rule.decision.impl.RaiseDecision;
import table.vo.privateinfo.PlayerPrivateVO;
import table.vo.publicinfo.PublicVO;

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
@Log4j2
public class RobotGamePlayerDoPreset implements PlayerController {
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
    public void updatePublicInfo(PublicVO publicVO) {
    }

    @Override
    public void updatePrivateInfo(PlayerPrivateVO privateInfoVO) {
        log.trace("Updating private info: {}", privateInfoVO);
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
