package table;

import table.player.CardPlayer;
import util.ApplicationResult;

public interface CardTable {
    void setMaxPlayers(int maxPlayers);
    int getMaxPlayers();
    ApplicationResult playerJoin(CardPlayer cardPlayer);
    ApplicationResult playerLeave(CardPlayer cardPlayer);
    void startRound();
}
