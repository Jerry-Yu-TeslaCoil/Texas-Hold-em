package table.player.impl;

import table.player.CardPlayer;
import table.player.PlayerIterator;
import table.player.PlayerList;
import util.ApplicationResult;
import util.MathUtil;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * PlayerCoil represents a coil of players, like a round table. Pointer goes round and round.
 *
 * <p>
 *     This is used for normal round, where players are seated on a round table, make decisions by turn.
 * </p>
 *
 * <p>
 *     This is not thread-safe.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public class PlayerCoil implements PlayerList {
    private final List<CardPlayer> players;
    private int buttonPosition;
    private int maxPlayers;
    private long editionIndex;

    /**
     * Construct a PlayerCoil with default max player 22 and button position 0.
     */
    public PlayerCoil() {
        players = new ArrayList<>();
        buttonPosition = 0;
        this.maxPlayers = 22;
        this.editionIndex = 0;
    }

    /**
     * Construct a PlayerCoil with appointed players, default max player 22 and button position 0.
     * @param players Players who join the game.
     */
    public PlayerCoil(List<CardPlayer> players) {
        this.players = new ArrayList<>(players);
        this.buttonPosition = 0;
        this.maxPlayers = 22;
        this.editionIndex = 0;
    }

    @Override
    public int getPlayerNum() {
        return players.size();
    }

    @Override
    public boolean isEmpty() {
        return players.isEmpty();
    }

    @Override
    public CardPlayer getPlayerAt(int position) {
        if (players.isEmpty()) return null;
        int index = normalizeIndex(position);
        return players.get(index);
    }

    @Override
    public void nextRound() {
        buttonPosition = normalizeIndex(buttonPosition + 1);
    }

    @Override
    public int getButtonPosition() {
        return buttonPosition;
    }

    @Override
    public List<CardPlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    @Override
    public ApplicationResult addPlayer(CardPlayer player) {
        if (players.size() < maxPlayers) {
            players.add(player);
            this.editionIndex++;
            return ApplicationResult.success();
        }
        return ApplicationResult.error("The table cannot join any more players.");
    }

    @Override
    public ApplicationResult removePlayer(CardPlayer player) {
        int index = players.indexOf(player);
        if (index != -1) {
            players.remove(index);
            adjustPositionsAfterRemoval(index);
            this.editionIndex++;
            return ApplicationResult.success();
        }
        return ApplicationResult.error("Player not found.");
    }

    @Override
    public PlayerIterator getIterator() {
        return new PlayerCoilIterator(0, false);
    }

    @Override
    public PlayerIterator getIterator(int position) {
        return new PlayerCoilIterator(position, false);
    }

    @Override
    public PlayerIterator getIteratorFromAfterButton() {
        return new PlayerCoilIterator(buttonPosition + 1, false);
    }

    @Override
    public PlayerIterator getLoopIterator(int position) {
        return new PlayerCoilIterator(position, true);
    }

    @Override
    public String toString() {
        return "PlayerCoil{size=" + players.size() +
                ", button=" + buttonPosition + "}";
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public CardPlayer getSmallBlindPlayer() {
        return getPlayerAt(buttonPosition + 1);
    }

    @Override
    public CardPlayer getBigBlindPlayer() {
        return getPlayerAt(buttonPosition + 2);
    }

    public class PlayerCoilIterator extends PlayerIterator {
        private final int startIndex;
        private final boolean loop;
        private int currentIndex;
        private boolean returnedFirst;
        private final long recordEditionIndex;

        private PlayerCoilIterator(int startPosition, boolean loop) {
            if (players.isEmpty()) {
                this.startIndex = 0;
                this.currentIndex = 0;
            } else {
                this.startIndex = normalizeIndex(startPosition);
                this.currentIndex = this.startIndex;
            }
            this.returnedFirst = false;
            this.loop = false;
            this.recordEditionIndex = PlayerCoil.this.editionIndex;
        }

        @Override
        public boolean hasNext() {
            if (players.isEmpty()) return false;

            if (loop) return true;

            return !returnedFirst || currentIndex != startIndex;
        }

        @Override
        public CardPlayer next() {
            if (this.recordEditionIndex != PlayerCoil.this.editionIndex) {
                throw new ConcurrentModificationException("Modification of player list during iteration is not allowed.");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("No more players in the coil");
            }

            CardPlayer player = getPlayerAt(currentIndex);

            currentIndex = normalizeIndex(currentIndex + 1);

            if (!returnedFirst && !loop) {
                returnedFirst = true;
            }
            return player;
        }

        @Override
        public void jumpTo(int position) {
            if (players.isEmpty()) return;
            currentIndex = normalizeIndex(position);
        }

        @Override
        public int getCurrentPosition() {
            return currentIndex;
        }

        @Override
        public int getStartPosition() {
            return startIndex;
        }
    }

    private void adjustPositionsAfterRemoval(int removedIndex) {
        if (players.isEmpty()) {
            buttonPosition = 0;
            return;
        }
        if (buttonPosition > removedIndex) {
            buttonPosition--;
        } else if (buttonPosition == removedIndex) {
            buttonPosition = normalizeIndex(buttonPosition); // 确保在范围内
        }
    }

    private int normalizeIndex(int position) {
        return MathUtil.normalizeIndex(position, players.size());
    }
}