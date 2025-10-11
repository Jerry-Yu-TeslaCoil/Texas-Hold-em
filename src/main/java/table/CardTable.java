package table;

import table.player.CardPlayer;
import util.ApplicationResult;

public interface CardTable {
    void setMaxPlayers(int maxPlayers);
    int getMaxPlayers();
    //TODO: DO NOT insert a CardPlayer. Use other player entity that can help identify the player and create connection.
    ApplicationResult playerJoin(CardPlayer cardPlayer);
    ApplicationResult playerLeave(CardPlayer cardPlayer);
    void startRounds();
}
