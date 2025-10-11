package table.vo.publicinfo;

import table.card.PokerCard;

import java.math.BigDecimal;

/**
 * View object class for a player's public info set.
 *
 * <p>
 * All players' info:
 *     <ul>
 *         <li>Player's public info</li>
 *         <li>Two cards(Unrevealed until the end of round)</li>
 *         <li>Chips in hand</li>
 *         <li>Chips already invested</li>
 *         <li>If still continuing the game</li>
 *     </ul>
 * </p>
 *
 * @author jerry
 * @version 1.0
 */
public record PlayerGamePublicInfoVO(PlayerPublicInfoVO playerPublicInfoVO, PokerCard[] pokerCard,
                                     BigDecimal chipsInHand, BigDecimal chipsInvested, boolean isContinuingGame) {
}
