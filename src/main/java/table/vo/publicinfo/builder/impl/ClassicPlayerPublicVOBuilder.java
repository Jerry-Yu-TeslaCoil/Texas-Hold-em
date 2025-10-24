package table.vo.publicinfo.builder.impl;

import table.card.PokerCard;
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
        PlayerPublicVO.Builder builder = PlayerPublicVO.builder()
                .setPlayerPersonalVO(cardPlayer.getPlayerController().getPlayerPersonalVO())
                .setChipsInHand(cardPlayer.getStack()).setContinuingGame(cardPlayer.getIsContinuingGame());
        return switch (gameState) {
            case INIT -> builder.build();
            case PRE_FLOP, FLOP, TURN, RIVER -> builder.setChipsInvested(cardPlayer.getPlayerInvestment())
                    .build();
            case SHOWDOWN -> {
                builder = builder.setChipsInvested(cardPlayer.getPlayerInvestment());
                if (cardPlayer.getIsJoiningPot()) {
                    builder = builder.setPokerCard(cardPlayer.getHoleCards().toArray(new PokerCard[0]));
                }
                yield builder.build();
            }
            case END -> builder.setChipsInvested(cardPlayer.getPlayerInvestment())
                    .setPrize(cardPlayer.getPlayerPrize())
                    .build();
        };
    }
}