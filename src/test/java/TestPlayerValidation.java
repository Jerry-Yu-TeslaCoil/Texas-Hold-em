import control.impl.RobotPlayerDoPreset;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.mechanism.DecisionRequest;
import table.mechanism.DecisionType;
import table.mechanism.ResolvedAction;
import table.player.CardPlayer;
import table.player.impl.SimpleCardPlayer;

import java.math.BigDecimal;

@Log4j2
public class TestPlayerValidation {
    @Test
    public void testPlayerValidationCall() {
        RobotPlayerDoPreset controller = new RobotPlayerDoPreset();
        DecisionRequest decisionRequest = new DecisionRequest(new BigDecimal(12));
        CardPlayer cardPlayer = new SimpleCardPlayer(controller, new BigDecimal(24), 0);
        log.info(decisionRequest);
        ResolvedAction playerDecision = cardPlayer.getPlayerDecision(decisionRequest);
        log.info("Calling, Player decision: {}", playerDecision);
    }

    @Test
    public void testPlayerValidationCallAllin() {
        RobotPlayerDoPreset controller = new RobotPlayerDoPreset();
        DecisionRequest decisionRequest = new DecisionRequest(new BigDecimal(25));
        CardPlayer cardPlayer = new SimpleCardPlayer(controller, new BigDecimal(24), 0);
        log.info(decisionRequest);
        ResolvedAction playerDecision = cardPlayer.getPlayerDecision(decisionRequest);
        log.info("Calling all, Player decision: {}", playerDecision);
    }

    @Test
    public void testPlayerValidationRaise() {
        RobotPlayerDoPreset controller = new RobotPlayerDoPreset();
        controller.setReaction(DecisionType.RAISE, new BigDecimal(11));
        DecisionRequest decisionRequest = new DecisionRequest(new BigDecimal(12));
        CardPlayer cardPlayer = new SimpleCardPlayer(controller, new BigDecimal(24), 0);
        log.info(decisionRequest);
        ResolvedAction playerDecision = cardPlayer.getPlayerDecision(decisionRequest);
        log.info("Raising {}, Player decision: {}", 11, playerDecision);
    }

    @Test
    public void testPlayerValidationRaiseException() {
        RobotPlayerDoPreset controller = new RobotPlayerDoPreset();
        controller.setReaction(DecisionType.RAISE, new BigDecimal(13));
        DecisionRequest decisionRequest = new DecisionRequest(new BigDecimal(12));
        CardPlayer cardPlayer = new SimpleCardPlayer(controller, new BigDecimal(24), 0);
        log.info(decisionRequest);
        ResolvedAction playerDecision = cardPlayer.getPlayerDecision(decisionRequest);
        log.info("Raise too high, Player decision: {}", playerDecision);
    }
}
