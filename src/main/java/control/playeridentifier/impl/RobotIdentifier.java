package control.playeridentifier.impl;

import control.playeridentifier.PlayerIdentifier;
import control.vo.PlayerPersonalVO;

public class RobotIdentifier implements PlayerIdentifier {

    @Override
    public PlayerPersonalVO getPlayerPersonalVO() {
        return new PlayerPersonalVO();
    }
}
