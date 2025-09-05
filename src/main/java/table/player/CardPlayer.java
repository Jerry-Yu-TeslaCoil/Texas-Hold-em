package table.player;

import table.card.PokerCard;
import table.control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.PlayerDecision;

import java.math.BigDecimal;
import java.util.List;

public interface CardPlayer {
    void setPlayerController(PlayerController playerController);
    PlayerController getPlayerController();
    void setStack(BigDecimal stack);
    BigDecimal getStack();
    void receiveHoleCard(PokerCard card);
    List<PokerCard> getHoleCards();
    void clearHoleCard();
    boolean getIsContinuingGame();
    void setContinuingGame(boolean continuingGame);

    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest);
}
