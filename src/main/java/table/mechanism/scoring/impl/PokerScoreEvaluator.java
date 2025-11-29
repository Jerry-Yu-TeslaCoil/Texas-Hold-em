package table.mechanism.scoring.impl;

import table.card.PokerCard;
import table.card.Rank;
import table.card.Suit;
import table.mechanism.scoring.ScoreEvaluator;

import java.util.*;
import java.util.stream.Collectors;

public class PokerScoreEvaluator implements ScoreEvaluator {

    // 牌型分数基数（确保高牌型分数永远大于低牌型）
    private static final int HIGH_CARD_BASE = 100000000;
    private static final int ONE_PAIR_BASE = 200000000;
    private static final int TWO_PAIR_BASE = 300000000;
    private static final int THREE_OF_A_KIND_BASE = 400000000;
    private static final int STRAIGHT_BASE = 500000000;
    private static final int FLUSH_BASE = 600000000;
    private static final int FULL_HOUSE_BASE = 700000000;
    private static final int FOUR_OF_A_KIND_BASE = 800000000;
    private static final int STRAIGHT_FLUSH_BASE = 900000000;
    private static final int ROYAL_FLUSH_BASE = 1000000000;

    @Override
    public int score(List<PokerCard> cards) {
        if (cards == null || cards.size() != 7) {
            throw new IllegalArgumentException("必须提供7张牌");
        }

        // 按点数降序排序
        List<PokerCard> sortedCards = cards.stream()
                .sorted((c1, c2) -> Integer.compare(c2.rank().getValue(), c1.rank().getValue()))
                .collect(Collectors.toList());

        // 检查各种牌型（从最高到最低）
        int royalFlushScore = checkRoyalFlush(sortedCards);
        if (royalFlushScore > 0) return royalFlushScore;

        int straightFlushScore = checkStraightFlush(sortedCards);
        if (straightFlushScore > 0) return straightFlushScore;

        int fourOfAKindScore = checkFourOfAKind(sortedCards);
        if (fourOfAKindScore > 0) return fourOfAKindScore;

        int fullHouseScore = checkFullHouse(sortedCards);
        if (fullHouseScore > 0) return fullHouseScore;

        int flushScore = checkFlush(sortedCards);
        if (flushScore > 0) return flushScore;

        int straightScore = checkStraight(sortedCards);
        if (straightScore > 0) return straightScore;

        int threeOfAKindScore = checkThreeOfAKind(sortedCards);
        if (threeOfAKindScore > 0) return threeOfAKindScore;

        int twoPairScore = checkTwoPair(sortedCards);
        if (twoPairScore > 0) return twoPairScore;

        int onePairScore = checkOnePair(sortedCards);
        if (onePairScore > 0) return onePairScore;

        return checkHighCard(sortedCards);
    }

    private int checkRoyalFlush(List<PokerCard> cards) {
        // 皇家同花顺是10-J-Q-K-A的同花顺
        for (Suit suit : Suit.values()) {
            boolean hasTen = false, hasJack = false, hasQueen = false, hasKing = false, hasAce = false;

            for (PokerCard card : cards) {
                if (card.suit() == suit) {
                    if (card.rank() == Rank.TEN) hasTen = true;
                    if (card.rank() == Rank.JACK) hasJack = true;
                    if (card.rank() == Rank.QUEEN) hasQueen = true;
                    if (card.rank() == Rank.KING) hasKing = true;
                    if (card.rank() == Rank.ACE) hasAce = true;
                }
            }

            if (hasTen && hasJack && hasQueen && hasKing && hasAce) {
                return ROYAL_FLUSH_BASE;
            }
        }
        return 0;
    }

    private int checkStraightFlush(List<PokerCard> cards) {
        // 按花色分组
        Map<Suit, List<PokerCard>> cardsBySuit = cards.stream()
                .collect(Collectors.groupingBy(PokerCard::suit));

        for (List<PokerCard> suitedCards : cardsBySuit.values()) {
            if (suitedCards.size() >= 5) {
                // 对同花色的牌按点数排序
                List<PokerCard> sortedSuitedCards = suitedCards.stream()
                        .sorted((c1, c2) -> Integer.compare(c2.rank().getValue(), c1.rank().getValue()))
                        .collect(Collectors.toList());

                int straightFlushScore = findStraight(sortedSuitedCards);
                if (straightFlushScore > 0) {
                    return STRAIGHT_FLUSH_BASE + straightFlushScore;
                }
            }
        }
        return 0;
    }

    private int checkFourOfAKind(List<PokerCard> cards) {
        Map<Rank, Integer> rankCount = getRankCount(cards);

        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 4) {
                int fourRankValue = entry.getKey().getValue();
                // 找到最大的单牌作为踢脚
                int kicker = findKickers(cards, List.of(entry.getKey()), 1);
                return FOUR_OF_A_KIND_BASE + fourRankValue * 100 + kicker;
            }
        }
        return 0;
    }

    private int checkFullHouse(List<PokerCard> cards) {
        Map<Rank, Integer> rankCount = getRankCount(cards);

        Rank threeOfAKind = null;
        Rank pair = null;

        // 找三条
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() >= 3) {
                if (threeOfAKind == null || entry.getKey().getValue() > threeOfAKind.getValue()) {
                    threeOfAKind = entry.getKey();
                }
            }
        }

        if (threeOfAKind == null) return 0;

        // 找对子（不能是三条的牌）
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() >= 2 && !entry.getKey().equals(threeOfAKind)) {
                if (pair == null || entry.getKey().getValue() > pair.getValue()) {
                    pair = entry.getKey();
                }
            }
        }

        if (pair == null) {
            // 如果没有单独的对子，检查是否有第二个三条
            for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
                if (entry.getValue() >= 3 && !entry.getKey().equals(threeOfAKind)) {
                    pair = entry.getKey();
                    break;
                }
            }
        }

        if (pair != null) {
            return FULL_HOUSE_BASE + threeOfAKind.getValue() * 100 + pair.getValue();
        }

        return 0;
    }

    private int checkFlush(List<PokerCard> cards) {
        Map<Suit, List<PokerCard>> cardsBySuit = cards.stream()
                .collect(Collectors.groupingBy(PokerCard::suit));

        for (List<PokerCard> suitedCards : cardsBySuit.values()) {
            if (suitedCards.size() >= 5) {
                // 取同花中最大的5张牌
                List<PokerCard> sortedFlushCards = suitedCards.stream()
                        .sorted((c1, c2) -> Integer.compare(c2.rank().getValue(), c1.rank().getValue()))
                        .limit(5)
                        .toList();

                // 计算分数：最高牌 * 16^4 + 次高牌 * 16^3 + ...
                int score = 0;
                for (int i = 0; i < sortedFlushCards.size(); i++) {
                    score += sortedFlushCards.get(i).rank().getValue() * (int) Math.pow(16, 4 - i);
                }
                return FLUSH_BASE + score;
            }
        }
        return 0;
    }

    private int checkStraight(List<PokerCard> cards) {
        // 去重并按点数排序
        List<PokerCard> uniqueCards = cards.stream()
                .collect(Collectors.toMap(
                        card -> card.rank().getValue(),
                        card -> card,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c2.rank().getValue(), c1.rank().getValue()))
                .collect(Collectors.toList());

        int straightScore = findStraight(uniqueCards);
        if (straightScore > 0) {
            return STRAIGHT_BASE + straightScore;
        }
        return 0;
    }

    private int findStraight(List<PokerCard> cards) {
        if (cards.size() < 5) return 0;

        // 检查A-5-4-3-2的特殊顺子
        boolean hasAce = cards.get(0).rank() == Rank.ACE;
        boolean hasFive = false, hasFour = false, hasThree = false, hasTwo = false;

        for (PokerCard card : cards) {
            if (card.rank() == Rank.FIVE) hasFive = true;
            if (card.rank() == Rank.FOUR) hasFour = true;
            if (card.rank() == Rank.THREE) hasThree = true;
            if (card.rank() == Rank.TWO) hasTwo = true;
        }

        if (hasAce && hasFive && hasFour && hasThree && hasTwo) {
            return Rank.FIVE.getValue(); // 5-high straight
        }

        // 检查普通顺子
        for (int i = 0; i <= cards.size() - 5; i++) {
            int currentRank = cards.get(i).rank().getValue();
            boolean isStraight = true;

            for (int j = 1; j < 5; j++) {
                if (cards.get(i + j).rank().getValue() != currentRank - j) {
                    isStraight = false;
                    break;
                }
            }

            if (isStraight) {
                return currentRank;
            }
        }

        return 0;
    }

    private int checkThreeOfAKind(List<PokerCard> cards) {
        Map<Rank, Integer> rankCount = getRankCount(cards);

        Rank threeOfAKind = null;
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 3) {
                if (threeOfAKind == null || entry.getKey().getValue() > threeOfAKind.getValue()) {
                    threeOfAKind = entry.getKey();
                }
            }
        }

        if (threeOfAKind != null) {
            int kickers = findKickers(cards, List.of(threeOfAKind), 2);
            return THREE_OF_A_KIND_BASE + threeOfAKind.getValue() * 10000 + kickers;
        }

        return 0;
    }

    private int checkTwoPair(List<PokerCard> cards) {
        Map<Rank, Integer> rankCount = getRankCount(cards);

        List<Rank> pairs = new ArrayList<>();
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() >= 2) {
                pairs.add(entry.getKey());
            }
        }

        if (pairs.size() >= 2) {
            // 按点数降序排序
            pairs.sort((r1, r2) -> Integer.compare(r2.getValue(), r1.getValue()));
            Rank highPair = pairs.get(0);
            Rank lowPair = pairs.get(1);

            int kicker = findKickers(cards, Arrays.asList(highPair, lowPair), 1);
            return TWO_PAIR_BASE + highPair.getValue() * 10000 + lowPair.getValue() * 100 + kicker;
        }

        return 0;
    }

    private int checkOnePair(List<PokerCard> cards) {
        Map<Rank, Integer> rankCount = getRankCount(cards);

        Rank pair = null;
        for (Map.Entry<Rank, Integer> entry : rankCount.entrySet()) {
            if (entry.getValue() == 2) {
                if (pair == null || entry.getKey().getValue() > pair.getValue()) {
                    pair = entry.getKey();
                }
            }
        }

        if (pair != null) {
            int kickers = findKickers(cards, List.of(pair), 3);
            return ONE_PAIR_BASE + pair.getValue() * 1000000 + kickers;
        }

        return 0;
    }

    private int checkHighCard(List<PokerCard> cards) {
        // 取最大的5张牌
        List<PokerCard> topFive = cards.stream()
                .sorted((c1, c2) -> Integer.compare(c2.rank().getValue(), c1.rank().getValue()))
                .limit(5)
                .toList();

        // 计算分数：最高牌 * 16^4 + 次高牌 * 16^3 + ...
        int score = 0;
        for (int i = 0; i < topFive.size(); i++) {
            score += topFive.get(i).rank().getValue() * (int) Math.pow(16, 4 - i);
        }
        return HIGH_CARD_BASE + score;
    }

    private Map<Rank, Integer> getRankCount(List<PokerCard> cards) {
        Map<Rank, Integer> rankCount = new HashMap<>();
        for (PokerCard card : cards) {
            rankCount.put(card.rank(), rankCount.getOrDefault(card.rank(), 0) + 1);
        }
        return rankCount;
    }

    private int findKickers(List<PokerCard> cards, List<Rank> excludedRanks, int numKickers) {
        List<PokerCard> kickerCandidates = cards.stream()
                .filter(card -> !excludedRanks.contains(card.rank()))
                .sorted((c1, c2) -> Integer.compare(c2.rank().getValue(), c1.rank().getValue()))
                .toList();

        int score = 0;
        for (int i = 0; i < Math.min(numKickers, kickerCandidates.size()); i++) {
            score += kickerCandidates.get(i).rank().getValue() * (int) Math.pow(16, numKickers - 1 - i);
        }
        return score;
    }
}
