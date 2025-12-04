package table.state.gamestate;

import lombok.*;
import table.card.CardDeck;
import table.card.PokerCard;
import table.config.TableConfig;
import table.player.PlayerList;
import table.pot.PotManager;
import table.vo.publicinfo.TablePublicVO;
import table.vo.publicinfo.builder.PlayerPublicVOBuilder;

import java.math.BigDecimal;

/**
 * Context class serving the game state, providing necessary info.
 *
 * <p>
 *     This is only used for game states.
 *     It provides the player list of the game, which includes other necessary information.
 *     For example, player's stack in hand, hole cards, button position, etc.
 *     To keep isolation, context should not provide exact iterator of the list.
 *     It also provides a pot manager for stack settlement.
 * </p>
 *
 * <p>
 *     This is an info-class.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameStateContext {

    //RoundIndex for marking game round
    private int roundIndex;

    //Total rounds for the game to finish
    private int totalRounds;

    //The number of active players
    private int activePlayerNum;

    //The number of players who can still make decisions
    private int decidingPlayerNum;

    //If the po already judged
    private boolean isPotJudged;

    //PlayerList for game proceeding
    private PlayerList players;

    //PotManager for pot managing
    private PotManager potManager;

    //CardDeck for public cards
    private CardDeck cardDeck;

    //GameConfig for info like least blind
    private TableConfig tableConfig;

    //Public cards
    private PokerCard[] publicCards;

    //Player VO builder
    private PlayerPublicVOBuilder playerPublicVOBuilder;

    //Table VO builder
    private TablePublicVO.Builder tablePublicVOBuilder;

    //Num of chips required to invest in total of calling
    private BigDecimal betBasisLine;
}
