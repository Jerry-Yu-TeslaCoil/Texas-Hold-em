package table.state;

import table.mechanism.PlayerDecision;

public abstract class PokerState {
    public abstract PokerState execute(PlayerDecision playerDecision);
}
