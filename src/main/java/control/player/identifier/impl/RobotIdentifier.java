package control.player.identifier.impl;

import control.player.identifier.PlayerIdentifier;
import control.vo.PlayerPersonalVO;

public class RobotIdentifier implements PlayerIdentifier<String> {

    @Override
    public PlayerPersonalVO<String> getPlayerPersonalVO() {
        return new PlayerPersonalVO<>("Test Robot");
    }
}
