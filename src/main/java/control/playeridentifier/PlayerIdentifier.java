package control.playeridentifier;


import control.vo.PlayerPersonalVO;

/**
 * Interface of giving identification info from the player.
 *
 * <p>
 *     This is used to give player's public personal info, such as ID, name and avatar.
 *     These will be presented to other players during the game.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public interface PlayerIdentifier {

    /**
     * Get the player's info in a view object.
     * @return A view object of the player's info.
     */
    public PlayerPersonalVO getPlayerPersonalVO();
}
