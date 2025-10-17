package table.vo.publicinfo;

import control.vo.PlayerPersonalVO;
import table.card.PokerCard;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

/**
 * View object class for a player's public info set.
 *
 * <p>
 * All players' info:
 *     <ul>
 *         <li>Player's public info</li>
 *         <li>Two cards(Unrevealed until the end of round)</li>
 *         <li>Chips in hand</li>
 *         <li>Chips already invested</li>
 *         <li>If still continuing the game</li>
 *         <li>Player's prize at the end of the game</li>
 *     </ul>
 * </p>
 *
 * @author jerry
 * @version 1.0
 */
public record PlayerPublicVO(PlayerPersonalVO playerPersonalVO, PokerCard[] pokerCard,
                             BigDecimal chipsInHand, BigDecimal chipsInvested,
                             boolean isContinuingGame, BigDecimal prize) {
    /**
     * Create a view object by given parameters.
     *
     * @param playerPersonalVO The player's personal public info.
     * @param pokerCard          The player's hole card(Only given when the game comes to an end).
     * @param chipsInHand        The player's chips.
     * @param chipsInvested      Chips the player already invested.
     * @param isContinuingGame   If the player is still continuing the game.
     * @param prize              The amount of chips the player wins.
     */
    public PlayerPublicVO {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerPublicVO) obj;
        return Objects.equals(this.playerPersonalVO, that.playerPersonalVO) &&
                Arrays.equals(this.pokerCard, that.pokerCard) &&
                Objects.equals(this.chipsInHand, that.chipsInHand) &&
                Objects.equals(this.chipsInvested, that.chipsInvested) &&
                this.isContinuingGame == that.isContinuingGame;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerPersonalVO, Arrays.hashCode(pokerCard), chipsInHand, chipsInvested, isContinuingGame);
    }

    @Override
    public String toString() {
        return "PlayerPublicVO[" +
                "playerPersonalVO=" + playerPersonalVO + ", " +
                "pokerCard=" + Arrays.toString(pokerCard) + ", " +
                "chipsInHand=" + chipsInHand + ", " +
                "chipsInvested=" + chipsInvested + ", " +
                "isContinuingGame=" + isContinuingGame + ", " +
                "prize=" + prize + "]";
    }

    /**
     * Get a new builder instance of the VO.
     *
     * @return a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The builder of the view object.
     */
    public static class Builder {
        private PlayerPersonalVO playerPersonalVO = null;
        private PokerCard[] pokerCard = null;
        private BigDecimal chipsInHand = null;
        private BigDecimal chipsInvested = null;
        private boolean isContinuingGame = false;
        private BigDecimal prize = null;

        public Builder setPlayerPersonalVO(PlayerPersonalVO playerPersonalVO) {
            this.playerPersonalVO = playerPersonalVO;
            return this;
        }

        public Builder setPokerCard(PokerCard[] pokerCard) {
            this.pokerCard = pokerCard;
            return this;
        }

        public Builder setChipsInHand(BigDecimal chipsInHand) {
            this.chipsInHand = chipsInHand;
            return this;
        }

        public Builder setChipsInvested(BigDecimal chipsInvested) {
            this.chipsInvested = chipsInvested;
            return this;
        }

        public Builder setContinuingGame(boolean isContinuingGame) {
            this.isContinuingGame = isContinuingGame;
            return this;
        }

        public Builder setPrize(BigDecimal prize) {
            this.prize = prize;
            return this;
        }

        public PlayerPublicVO build() {
            return new PlayerPublicVO(this.playerPersonalVO,
                    this.pokerCard,
                    this.chipsInHand,
                    this.chipsInvested,
                    this.isContinuingGame,
                    this.prize);
        }
    }
}
