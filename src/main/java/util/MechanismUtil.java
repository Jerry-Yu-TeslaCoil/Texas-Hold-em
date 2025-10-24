package util;


import table.card.PokerCard;
import table.mechanism.scoring.ScoreEvaluator;
import table.mechanism.scoring.impl.PokerScoreEvaluator;

import java.util.List;

public class MechanismUtil {

    public static int getScore(List<PokerCard> cards) {
        ScoreEvaluator evaluator = new PokerScoreEvaluator();
        return evaluator.score(cards);
    }
}
