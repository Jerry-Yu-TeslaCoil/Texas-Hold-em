package pojo.gamestate;

import pojo.PlayerDecision;

public abstract class PokerState {
    public abstract PokerState execute(PlayerDecision playerDecision);
}
