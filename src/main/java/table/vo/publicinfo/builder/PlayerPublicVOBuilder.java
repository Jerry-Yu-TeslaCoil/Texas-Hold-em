package table.vo.publicinfo.builder;

import table.player.CardPlayer;
import table.state.GameState;
import table.vo.publicinfo.PlayerPublicVO;

/**
 * Builder class for PlayerPublicInfoVO.
 *
 * <p>
 *     This class is used to build player's game info to public VO.
 *     It shall filter the messages according to the state of the table.
 * </p>
 *
 * <p>
 *     To use this, firstly set the state, then for every CardPlayer, build.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PlayerPublicVOBuilder {

    /**
     * Set the current state of the table.
     */
    public void setState(GameState state);

    /**
     * Build the player's public info VO according to the set state.
     * @param cardPlayer The CardPlayer whose info needs to be built to VO.
     * @return The player's public info VO.
     */
    public PlayerPublicVO build(CardPlayer cardPlayer);
}
