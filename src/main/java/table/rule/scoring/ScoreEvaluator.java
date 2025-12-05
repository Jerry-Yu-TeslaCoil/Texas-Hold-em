package table.rule.scoring;

import table.card.PokerCard;

import java.util.List;

public interface ScoreEvaluator {
    int score(List<PokerCard> card);
}
