package table.pot;

import table.card.PokerCard;
import table.player.CardPlayer;
import util.MechanismUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The score item class of the player cards.
 *
 * <p>
 *     This item is used for player hole card ranking.
 *     Put this in a priority queue will give the ranking.
 * </p>
 *
 * @author jerry
 *
 * @version 1.0
 */
public final class PlayerRanking implements Comparable<PlayerRanking> {
    private final CardPlayer player;
    private final int score;

    /**
     * Construct the rank with appointed player and score.
     * @param player The ranking player.
     * @param score The appointed score.
     */
    public PlayerRanking(CardPlayer player, int score) {
        this.player = player;
        this.score = score;
    }

    /**
     * Construct the rank with player and the public cards.
     * The score will be computed automatically.
     * @param cardPlayer The ranking player.
     * @param publicCards The public cards of this game.
     */
    public PlayerRanking(CardPlayer cardPlayer, List<PokerCard> publicCards) {
        this.player = cardPlayer;
        List<PokerCard> cards = new ArrayList<>(publicCards);
        cards.addAll(cardPlayer.getHoleCards());
        this.score = MechanismUtil.getScore(cards);
    }

    /**
     * Get the ranking player.
     * @return The player.
     */
    public CardPlayer getPlayer() {
        return player;
    }

    /**
     * Get the score of the player's hole cards.
     * @return The hole cards' score.
     */
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
