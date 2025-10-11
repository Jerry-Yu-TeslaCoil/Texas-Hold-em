package control;

import table.card.PokerCard;
import table.mechanism.DecisionRequest;
import table.mechanism.PlayerDecision;
import table.vo.privateinfo.PlayerGamePrivateInfoVO;
import table.vo.publicinfo.PublicInfoVO;

/**
 * Interface representing player side information.
 *
 * <p>
 *     For functioning, the interface provides methods for sending information and receiving decision.
 *     For example, addHoleCard() receives a card from table and present to player clients.
 *     For anti-cheating, the interface provides methods only under strict authorities.
 * </p>
 *
 * <p>
 *     To ensure high safety restrictions, information is sent to player and shall never return.
 *     All changes are included in the decision and shall not be pointing to any certain one.
 *     That means decisions should only contain basic operation.
 *     For example, shown cards should be provided as hashcode or position index, not certain card info.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PlayerController {

    /**
     * Dispatch current public information to the player.
     * @param publicInfoVO The view object containing all public information on the table.
     */
    void updatePublicInfo(PublicInfoVO publicInfoVO);

    /**
     * Dispatch player's private information in the game.
     * @param privateInfoVO The view object containing the player's private game info.
     */
    void updatePrivateInfo(PlayerGamePrivateInfoVO privateInfoVO);

    /**
     * Ask the player to make a decision.
     * @param decisionRequest Limits for decision, like the least bet.
     * @return Decision for the current circumstance.
     */
    PlayerDecision getPlayerDecision(DecisionRequest decisionRequest);
}
