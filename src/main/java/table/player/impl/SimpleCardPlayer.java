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

/**
 * Simple CardPlayer Implementation.
 *
 * <p>
 *     This is used for the first impl version, and no other possible needed version yet.
 *     SimpleCardPlayer accepts a controller for player action control,
 *     and a BigDecimal for storing stack.
 *     Call getPlayerDecision() will only pass request directly to player.
 * </p>
 *
 * <p>
 *     This is not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class SimpleCardPlayer implements CardPlayer {

    private PlayerController controller;

    private BigDecimal stack;
    private final List<PokerCard> pokerCards;

    private boolean isContinuingGame;

    /**
     * Construct a simple card player with appointed controlling player and BigDecimal of stack.
     * @param controller Controlling player could be a real player terminal or a robot.
     * @param stack The stack the player currently have.
     */
    public SimpleCardPlayer(PlayerController controller, BigDecimal stack) {
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
