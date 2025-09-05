package table.control;

import table.card.PokerCard;
import table.mechanism.DecisionRequest;
import table.mechanism.PlayerDecision;

public interface PlayerController {
    void addHoleCard(PokerCard pokerCard);
    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest);
}
