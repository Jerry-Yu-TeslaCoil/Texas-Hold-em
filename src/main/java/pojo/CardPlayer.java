package pojo;

import java.math.BigDecimal;

public interface CardPlayer {
    void setPlayerController(PlayerController playerController);
    PlayerController getPlayerController();
    void setStack(BigDecimal stack);
    BigDecimal getStack();
    void receiveHoleCard(PokerCard card);
    void clearHoleCard();
    boolean getIsContinuingGame();
    void setContinuingGame(boolean continuingGame);

    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest);
}
