package table.control.impl;

import table.card.PokerCard;
import table.control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.DecisionType;
import table.mechanism.PlayerDecision;

import java.util.ArrayList;

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

    public PCPlayerControl() {
        this.pokerCards = new ArrayList<>();
    }
}
