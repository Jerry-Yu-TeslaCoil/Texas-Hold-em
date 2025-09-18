package table.control.impl;

import table.card.PokerCard;
import table.control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;

import java.util.ArrayList;

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
public class PCPlayerControl implements PlayerController {

    private final ArrayList<PokerCard> pokerCards;

    @Override
    public void addHoleCard(PokerCard pokerCard) {
        this.pokerCards.add(pokerCard);
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
    public PCPlayerControl() {
        this.pokerCards = new ArrayList<>();
    }
}
