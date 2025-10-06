package table.state.gamestate;

// 状态处理器接口
public interface GameStateHandler {
    GameState execute(GameStateContext context);
}
