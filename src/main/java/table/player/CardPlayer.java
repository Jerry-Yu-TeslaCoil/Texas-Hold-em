package table.player;

import table.card.PokerCard;
import table.control.PlayerController;
import table.mechanism.DecisionRequest;
import table.mechanism.PlayerDecision;

import java.math.BigDecimal;
import java.util.List;

/**
 * Card player class.
 *
 * <p>
 *     It represents the player on the table, NOT the player that plays the game.
 *     To be clear, it is part of the table, or a sector of the table,
 *     so it should be a safe, authorized zone as well.
 *     DO NOT give any authority of this part directly to the player control.
 * </p>
 *
 * <p>
 *     This is considered not thread-safe. Do not use it in multi-thread circumstances.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface CardPlayer {

    /**
     * Get the identification of the player.
     * @return The ID.
     */
    int getID();

    /**
     * Set the controller of this CardPlayer. It may be a client side terminal, an AI robot, or a test mirror.
     * @param playerController The controller of this CardPlayer.
     */
    void setPlayerController(PlayerController playerController);

    /**
     * Get the controller of this CardPlayer. This is usually used for ID info broadcasting.
     * @return The controller of the CardPlayer.
     */
    PlayerController getPlayerController();

    /**
     * Set the stack of the current CardPlayer.
     * @param stack The stack of the CardPlayer.
     */
    void setStack(BigDecimal stack);

    /**
     * Get the stack the current player have.
     * @return The stack value.
     */
    BigDecimal getStack();

    /**
     * Send a hole card to CardPlayer.
     * @param card The hole card to be sent.
     */
    void receiveHoleCard(PokerCard card);

    /**
     * Get the hole cards the CardPlayer have.
     * @return The hole cards.
     */
    List<PokerCard> getHoleCards();

    /**
     * Remove all hole cards of the CardPlayer.
     */
    void clearHoleCard();

    /**
     * Get if the CardPlayer is still in the game.
     * The player is considered not continuing the game when he FOLDs, disconnects, or escapes from the table.
     * @return If the player is still continuing the game.
     */
    boolean getIsContinuingGame();

    /**
     * Set if the CardPlayer is still in the game.
     * The player is considered not continuing the game when he FOLDs, disconnects, or escapes from the table.
     * @param continuingGame Whether the player is still in the game.
     */
    void setIsContinuingGame(boolean continuingGame);

    /**
     * Get if the player is already all-in.
     * The player is considered all-in when his stack comes to 0 and is still continuing the game.
     * @return Whether the player is still in the game.
     */
    boolean getIsAllIn();

    /**
     * Require CardPlayer to make a decision.
     * @param decisionRequest Game context and decision limits, like the least required bet value.
     * @return The decision of the CardPlayer.
     */
    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest);
}
