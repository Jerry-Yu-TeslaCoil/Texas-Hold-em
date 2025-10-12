package table.vo.publicinfo.builder.impl;

import table.player.CardPlayer;
import table.state.gamestate.GameState;
import table.vo.publicinfo.PlayerPublicVO;
import table.vo.publicinfo.builder.PlayerPublicVOBuilder;

/**
 * The CardPlayer public info VO builder of the classic rule version.
 *
 * <p>
 *     This is used for the classic rule VO building.
 *     It filters:
 *     <ul>
 *         <li>Player's personal info, chips in hand before PRE-FLOP state;</li>
 *         <li>Apart from above, player's chips invested before the game ends;</li>
 *         <li>Apart from above, player's hole cards and prize after the game ends and settled.</li>
 *     </ul>
 * </p>
 *
 * <p>
 *     The builder is not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class ClassicPlayerPublicVOBuilder implements PlayerPublicVOBuilder {

    private GameState gameState;

    @Override
    public void setState(GameState state) {
        this.gameState = state;
    }

    @Override
    public PlayerPublicVO build(CardPlayer cardPlayer) {
        PlayerPublicVO.Builder builder = PlayerPublicVO.builder();
        return switch (gameState) {
            case INIT -> builder.setPlayerPersonalVO(cardPlayer.getPlayerController().getPlayerPersonalVO())
                    .setChipsInHand(cardPlayer.getStack())
                    .setContinuingGame(cardPlayer.getIsContinuingGame())
                    .build();
            case PRE_FLOP -> null;
            case FLOP -> null;
            case TURN -> null;
            case RIVER -> null;
            case SHOWDOWN -> null;
            case END -> null;
        };
        //TODO: Finish build
    }
}
