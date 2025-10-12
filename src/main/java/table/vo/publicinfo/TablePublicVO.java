package table.vo.publicinfo;

import table.card.PokerCard;
import table.mechanism.ResolvedAction;
import table.state.gamestate.GameState;

import java.math.BigDecimal;

/**
 * View Object of table public information class.
 *
 * <p>
 * This class is a sub parameter of the PublicInfoVO, and contains information about the table.
 * Table information:
 *     <ul>
 *         <li>Basic bet</li>
 *         <li>Initial bet</li>
 *         <li>Current game state</li>
 *         <li>Current decision maker</li>
 *         <li>Made decision</li>
 *         <li>Public cards(Some revealed)</li>
 *     </ul>
 * </p>
 *
 * @author jerry
 * @version 1.0
 */
public record TablePublicVO(BigDecimal basicBet, BigDecimal initialBet, GameState currentGameState,
                            int currentDecisionMakerId, ResolvedAction madeDecision, PokerCard[] publicCards) {
}
