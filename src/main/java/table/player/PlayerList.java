package table.player;

import util.ApplicationResult;

import java.util.List;

public interface PlayerList {
    int getPlayerNum();
    boolean isEmpty();
    CardPlayer getPlayerAt(int position);
    void nextRound();
    int getButtonPosition();
    List<CardPlayer> getPlayers();
    int getMaxPlayers();

    PlayerIterator getIterator(int position);

    PlayerIterator getIteratorFromButton();

    PlayerIterator getLoopIterator(int position);

    void setMaxPlayers(int maxPlayers);
    ApplicationResult addPlayer(CardPlayer player);
    ApplicationResult removePlayer(CardPlayer player);
    PlayerIterator getIterator();

    CardPlayer getSmallBlindPlayer();

    CardPlayer getBigBlindPlayer();
}
