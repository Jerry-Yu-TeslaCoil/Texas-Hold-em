package pojo;

public interface PlayerController {
    void addHoleCard(PokerCard pokerCard);
    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest);
}
