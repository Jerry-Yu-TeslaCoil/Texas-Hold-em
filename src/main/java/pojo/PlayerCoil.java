package pojo;

import java.util.*;
import java.util.NoSuchElementException;

public class PlayerCoil {
    private final List<CardPlayer> players;
    private int buttonPosition;
    private int currentPosition;

    public PlayerCoil() {
        players = new ArrayList<>();
        buttonPosition = 0;
        currentPosition = 1;
    }

    public PlayerCoil(List<CardPlayer> players) {
        this.players = new ArrayList<>(players);
        this.buttonPosition = 0;
        this.currentPosition = 1;
    }

    public int getPlayerNum() {
        return players.size();
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public CardPlayer getCurrentPlayer() {
        return getPlayerAt(currentPosition);
    }

    public CardPlayer getPlayerAt(int position) {
        if (players.isEmpty()) return null;
        int index = normalizeIndex(position);
        return players.get(index);
    }

    public void nextPlayer() {
        if (players.isEmpty()) return;
        currentPosition = normalizeIndex(currentPosition + 1);
    }

    public void nextGame() {
        buttonPosition = normalizeIndex(buttonPosition + 1);
        currentPosition = normalizeIndex(buttonPosition + 1);
    }

    public boolean isTheLastPlayer() {
        return currentPosition == buttonPosition;
    }

    public int getButtonPosition() {
        return buttonPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public List<CardPlayer> getPlayers() {
        return new ArrayList<>(players);
    }

    public void addPlayer(CardPlayer player) {
        players.add(player);
    }

    public void removePlayer(CardPlayer player) {
        int index = players.indexOf(player);
        if (index != -1) {
            players.remove(index);
            adjustPositionsAfterRemoval(index);
        }
    }

    private void adjustPositionsAfterRemoval(int removedIndex) {
        if (players.isEmpty()) {
            buttonPosition = 0;
            currentPosition = 1;
            return;
        }

        if (buttonPosition > removedIndex) {
            buttonPosition--;
        } else if (buttonPosition == removedIndex) {
            buttonPosition = normalizeIndex(buttonPosition); // 确保在范围内
        }

        // 调整currentPosition
        if (currentPosition > removedIndex) {
            currentPosition--;
        } else if (currentPosition == removedIndex) {
            currentPosition = normalizeIndex(currentPosition); // 确保在范围内
        }
    }

    public int normalizeIndex(int position) {
        return normalizeIndex(position, players.size());
    }

    public CoilIterator getIterator() {
        return new CoilIterator(0, false);
    }

    public CoilIterator getIterator(int position) {
        return new CoilIterator(position, false);
    }

    public CoilIterator getIteratorFromCurrent() {
        return new CoilIterator(currentPosition, false);
    }

    public CoilIterator getIteratorFromButton() {
        return new CoilIterator(buttonPosition, false);
    }

    public CoilIterator getLoopIterator(int position) {
        return new CoilIterator(position, true);
    }

    @Override
    public String toString() {
        return "PlayerCoil{size=" + players.size() +
                ", button=" + buttonPosition +
                ", current=" + currentPosition + "}";
    }

    public class CoilIterator implements Iterator<CardPlayer> {
        private final int startIndex;
        private final boolean loop;
        private final int expectedPlayerNum;
        private int currentIndex;
        private boolean returnedFirst;

        private CoilIterator(int startPosition, boolean loop) {
            if (players.isEmpty()) {
                this.startIndex = 0;
                this.currentIndex = 0;
            } else {
                this.startIndex = normalizeIndex(startPosition);
                this.currentIndex = this.startIndex;
            }
            this.returnedFirst = false;
            this.loop = false;
            this.expectedPlayerNum = players.size();
        }

        @Override
        public boolean hasNext() {
            if (players.isEmpty()) return false;

            if (loop) return true;

            return !returnedFirst || currentIndex != startIndex;
        }

        @Override
        public CardPlayer next() {
            if (expectedPlayerNum != players.size()) {
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

        public void jumpTo(int position) {
            if (players.isEmpty()) return;
            currentIndex = normalizeIndex(position);
        }

        public int getCurrentPosition() {
            return currentIndex;
        }

        public int getStartPosition() {
            return startIndex;
        }

        public int getModifiedPlayerNum() {
            return PlayerCoil.this.players.size() - expectedPlayerNum;
        }
    }

    // 辅助方法
    public CardPlayer getSmallBlindPlayer() {
        return getPlayerAt(buttonPosition + 1);
    }

    public CardPlayer getBigBlindPlayer() {
        return getPlayerAt(buttonPosition + 2);
    }

    public void resetToFirstAfterButton() {
        currentPosition = normalizeIndex(buttonPosition + 1);
    }

    private static int normalizeIndex(int position, int size) {
        if (size == 0) return 0;
        return (position % size + size) % size;
    }
}