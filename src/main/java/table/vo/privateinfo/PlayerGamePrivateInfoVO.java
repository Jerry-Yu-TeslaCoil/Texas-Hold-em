package table.vo.privateinfo;


import table.card.PokerCard;

/**
 * View object class for CardPlayer's private info in game.
 *
 * <p>
 *     This is used to transmit private info to PlayerController.
 *     Should include:
 *     <ul>
 *         <li>Player's hole cards</li>
 *     </ul>
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public record PlayerGamePrivateInfoVO(PokerCard[] holeCards) {
}
