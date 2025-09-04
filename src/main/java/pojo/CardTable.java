package pojo;

public interface CardTable {
    void setMaxPlayers(int maxPlayers);
    int getMaxPlayers();
    void setMinPlayers(int minPlayers);
    int getMinPlayers();
    TableMemberResult playerJoin(CardPlayer cardPlayer);
    TableMemberResult playerLeave(CardPlayer cardPlayer);
    void startRounds();
}
