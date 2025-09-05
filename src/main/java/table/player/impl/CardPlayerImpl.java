package table.player.impl;

import exception.IllegalOperationException;
import table.card.PokerCard;
import table.control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.PlayerDecision;
import table.player.CardPlayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CardPlayerImpl implements CardPlayer {

    private PlayerController controller;

    private BigDecimal stack;
    private final ArrayList<PokerCard> pokerCards;

    private boolean isContinuingGame;

    public CardPlayerImpl(PlayerController controller, BigDecimal stack) {
        this.controller = controller;
        this.pokerCards = new ArrayList<>();
        this.stack = stack;
        this.isContinuingGame = true;
    }

    @Override
    public void setPlayerController(PlayerController playerController) {
        this.controller = playerController;
    }

    @Override
    public PlayerController getPlayerController() {
        return this.controller;
    }

    @Override
    public void setStack(BigDecimal stack) {
        this.stack = stack;
    }

    @Override
    public BigDecimal getStack() {
        return this.stack;
    }

    @Override
    public void receiveHoleCard(PokerCard card) {
        if (this.pokerCards.size() >= 2) {
            throw new IllegalOperationException("Card stack overflow");
        }
        this.pokerCards.add(card);
        this.controller.addHoleCard(card);
    }

    @Override
    public List<PokerCard> getHoleCards() {
        return this.pokerCards;
    }

    @Override
    public void clearHoleCard() {
        this.pokerCards.clear();
    }

    @Override
    public boolean getIsContinuingGame() {
        return this.isContinuingGame;
    }

    @Override
    public void setContinuingGame(boolean isContinuingGame) {
        this.isContinuingGame = isContinuingGame;
    }

    @Override
    public PlayerDecision getPlayerDecision(DecisionRequest decisionRequest) {
        System.out.println("Requires " + decisionRequest.leastStackRequest());
        return controller.getPlayerDecision(decisionRequest);
    }
}
