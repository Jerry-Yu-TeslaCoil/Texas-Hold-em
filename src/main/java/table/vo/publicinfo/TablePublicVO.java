package table.vo.publicinfo;

import table.card.PokerCard;
import table.mechanism.ResolvedAction;
import table.state.gamestate.GameState;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * View Object of table public information class.
 *
 * <p>
 * This class is a sub parameter of the PublicInfoVO, and contains information about the table.
 * Table information:
 *     <ul>
 *         <li>Basic bet</li>
 *         <li>Initial bet</li>
 *         <li>Current game state</li>
 *         <li>Current decision maker</li>
 *         <li>Made decision</li>
 *         <li>Public cards(Some revealed)</li>
 *     </ul>
 * </p>
 *
 * @author jerry
 * @version 1.0
 */
public record TablePublicVO(BigDecimal basicBet, BigDecimal initialBet, GameState currentGameState,
                            int currentDecisionMakerId, ResolvedAction madeDecision, PokerCard[] publicCards) {

    @Override
    public String toString() {
        return "TablePublicVO{" +
                "basicBet=" + basicBet +
                ", initialBet=" + initialBet +
                ", currentGameState=" + currentGameState +
                ",\n\t\tcurrentDecisionMakerId=" + currentDecisionMakerId +
                ", madeDecision=" + madeDecision +
                ", publicCards=" + Arrays.toString(publicCards) +
                '}';
    }

    /**
     * Get a builder of VO.
     * @return A builder of VO.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for TablePublicVO.
     *
     * <p>
     *     This is used for a template of VO.
     *     Builder pattern can reach to an easier management.
     * </p>
     */
    public static class Builder {
        private BigDecimal basicBet;
        private BigDecimal initialBet;
        private GameState currentGameState;
        private int currentDecisionMakerId;
        private ResolvedAction madeDecision;
        private PokerCard[] publicCards;

        public Builder setBasicBet(BigDecimal basicBet) {
            this.basicBet = basicBet;
            return this;
        }

        public Builder setInitialBet(BigDecimal initialBet) {
            this.initialBet = initialBet;
            return this;
        }

        public Builder setCurrentGameState(GameState currentGameState) {
            this.currentGameState = currentGameState;
            return this;
        }

        public Builder setCurrentDecisionMakerId(int currentDecisionMakerId) {
            this.currentDecisionMakerId = currentDecisionMakerId;
            return this;
        }

        public Builder setMadeDecision(ResolvedAction madeDecision) {
            this.madeDecision = madeDecision;
            return this;
        }

        public Builder setPublicCards(PokerCard[] publicCards) {
            this.publicCards = publicCards;
            return this;
        }

        public TablePublicVO build() {
            return new TablePublicVO(this.basicBet, this.initialBet, this.currentGameState,
                    this.currentDecisionMakerId, this.madeDecision, this.publicCards);
        }
    }
}
