package table.vo.privateinfo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
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
@Data
@Getter
@AllArgsConstructor
@ToString
public class PlayerPrivateVO {
    private final PokerCard[] holeCards;
}
