package table.state.gamestate;

import table.state.gamestate.impl.*;

/**
 * State enum for game state execution and swifts.
 *
 * <p>
 *     This is used for the main state of the game such as Init, Pre-flop, etc.
 *     To avoid bio-side dependency and keep isolations, player list should be provided as the context of the state.
 * </p>
 *
 * <p>
 *     To use this, keep calling execute() to execute processes and swift to the next state.
 * </p>
 *
 * <p>
 *     This is thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public enum GameState {
    INIT(InitStateHandler.INSTANCE),
    PRE_FLOP(PreFlopStateHandler.INSTANCE),
    FLOP(FlopStateHandler.INSTANCE),
    TURN(TurnStateHandler.INSTANCE),
    RIVER(RiverStateHandler.INSTANCE),
    SHOWDOWN(ShowdownStateHandler.INSTANCE),
    END(EndStateHandler.INSTANCE);

    private final GameStateHandler handler;

    GameState(GameStateHandler handler) {
        this.handler = handler;
    }

    public GameState execute(GameStateContext context) {
        return handler.execute(context);
    }
}

