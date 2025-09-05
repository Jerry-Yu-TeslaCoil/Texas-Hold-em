package table.pot;

import table.card.PokerCard;
import table.player.CardPlayer;
import util.MechanismUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PlayerRanking implements Comparable<PlayerRanking> {
    private final CardPlayer player;
    private final int score;

    public PlayerRanking(CardPlayer player, int score) {
        this.player = player;
        this.score = score;
    }

    public PlayerRanking(CardPlayer cardPlayer, List<PokerCard> publicCards) {
        this.player = cardPlayer;
        List<PokerCard> cards = new ArrayList<>(publicCards);
        cards.addAll(cardPlayer.getHoleCards());
        this.score = MechanismUtil.getScore(cards);
    }

    public CardPlayer getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "PlayerRanking{" +
                "player=" + player +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(PlayerRanking o) {
        return Integer.compare(o.score, score);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerRanking) obj;
        return Objects.equals(this.player, that.player) &&
                this.score == that.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, score);
    }
}
