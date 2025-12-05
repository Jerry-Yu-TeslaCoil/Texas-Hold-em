package control.player.identifier.impl;

import control.player.identifier.PlayerIdentifier;
import control.vo.PlayerPersonalVO;

public class RobotIdentifier implements PlayerIdentifier {

    @Override
    public PlayerPersonalVO getPlayerPersonalVO() {
        return new PlayerPersonalVO();
    }
}
