import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import table.card.PokerCard;
import table.card.Rank;
import table.card.Suit;
import table.card.impl.FixedPokerCard;
import util.MechanismUtil;

import java.util.List;

import static org.junit.Assert.assertTrue;

@Log4j2
public class MechanismUtilTest {

    /* ---------- 工具快速构造 ---------- */
    private static PokerCard card(String s) {
        int len = s.length();
        Rank r = Rank.fromSymbol(s.substring(0, len - 1));
        Suit su = switch (s.substring(len - 1).toLowerCase()) {
            case "c" -> Suit.clubs;
            case "d" -> Suit.diamonds;
            case "h" -> Suit.hearts;
            case "s" -> Suit.spades;
            case "j" -> Suit.jokers;
            default -> null;
        };
        return new FixedPokerCard(su, r);
    }

    @SafeVarargs
    private static List<PokerCard> of(String... ss) {
        return java.util.Arrays.stream(ss).map(MechanismUtilTest::card).toList();
    }


    /* ---------- 牌型层级 ---------- */
    @Test
    public void royalFlush_vs_straightFlush() {
        List<PokerCard> rf = of("AH", "KH", "QH", "JH", "10H", "3C", "2D"); // 皇家同花顺
        List<PokerCard> sf = of("KH", "QH", "JH", "10H", "9H", "3C", "2D"); // 同花顺K-high
        log.info(MechanismUtil.getScore(rf));
        log.info(MechanismUtil.getScore(sf));
        assertTrue(MechanismUtil.getScore(rf) > MechanismUtil.getScore(sf));
    }

    @Test
    public void straightFlush_vs_fourOfAKind() {
        List<PokerCard> sf = of("9H", "8H", "7H", "6H", "5H", "3C", "2D");
        List<PokerCard> fk = of("9C", "9D", "9H", "9S", "KC", "QD", "JC");
        log.info(MechanismUtil.getScore(sf));
        log.info(MechanismUtil.getScore(fk));
        assertTrue(MechanismUtil.getScore(sf) > MechanismUtil.getScore(fk));
    }

    @Test
    public void fourOfAKind_vs_fullHouse() {
        List<PokerCard> fk = of("KC", "KD", "KH", "KS", "QC", "QD", "JC");
        List<PokerCard> fh = of("QC", "QD", "QH", "2D", "2S", "3C", "4D");
        log.info(MechanismUtil.getScore(fk));
        log.info(MechanismUtil.getScore(fh));
        assertTrue(MechanismUtil.getScore(fk) > MechanismUtil.getScore(fh));
    }

    @Test
    public void fullHouse_vs_flush() {
        List<PokerCard> fh = of("JC", "JD", "JH", "3C", "3D", "2H", "2C");
        List<PokerCard> fl = of("AD", "JD", "8D", "5D", "3D", "KC", "QH");
        log.info(MechanismUtil.getScore(fh));
        log.info(MechanismUtil.getScore(fl));
        assertTrue(MechanismUtil.getScore(fh) > MechanismUtil.getScore(fl));
    }

    @Test
    public void flush_vs_straight() {
        List<PokerCard> fl = of("AH", "QH", "9H", "5H", "2H", "KC", "QD");
        List<PokerCard> st = of("7C", "6D", "5H", "4S", "3C", "KC", "QD");
        log.info(MechanismUtil.getScore(fl));
        log.info(MechanismUtil.getScore(st));
        assertTrue(MechanismUtil.getScore(fl) > MechanismUtil.getScore(st));
    }

    @Test
    public void straight_vs_threeOfAKind() {
        List<PokerCard> st = of("7H", "6D", "5C", "4S", "3H", "KC", "QD");
        List<PokerCard> tk = of("9C", "9D", "9H", "AC", "KS", "QD", "JC");
        log.info(MechanismUtil.getScore(st));
        log.info(MechanismUtil.getScore(tk));
        assertTrue(MechanismUtil.getScore(st) > MechanismUtil.getScore(tk));
    }

    @Test
    public void threeOfAKind_vs_twoPairs() {
        List<PokerCard> tk = of("8C", "8D", "8H", "AC", "KS", "QD", "JC");
        List<PokerCard> tp = of("7C", "7D", "6H", "6S", "AC", "KS", "QD");
        log.info(MechanismUtil.getScore(tk));
        log.info(MechanismUtil.getScore(tp));
        assertTrue(MechanismUtil.getScore(tk) > MechanismUtil.getScore(tp));
    }

    @Test
    public void twoPairs_vs_onePair() {
        List<PokerCard> tp = of("5C", "5D", "4H", "4S", "AC", "KS", "QD");
        List<PokerCard> op = of("QC", "QD", "KH", "9S", "8C", "7D", "6H");
        log.info(MechanismUtil.getScore(tp));
        log.info(MechanismUtil.getScore(op));
        assertTrue(MechanismUtil.getScore(tp) > MechanismUtil.getScore(op));
    }

    @Test
    public void onePair_vs_highCard() {
        List<PokerCard> op = of("KC", "KD", "AH", "QS", "JC", "9D", "7H");
        List<PokerCard> hc = of("AH", "QH", "JC", "9S", "8D", "7C", "5H");
        log.info(MechanismUtil.getScore(op));
        log.info(MechanismUtil.getScore(hc));
        assertTrue(MechanismUtil.getScore(op) > MechanismUtil.getScore(hc));
    }

    /* ---------- 同级踢脚 ---------- */
    @Test
    public void highCard_kicker() {
        List<PokerCard> high = of("AH", "QH", "JC", "9S", "7D", "5C", "3D");
        List<PokerCard> low  = of("KH", "QH", "JC", "9S", "7D", "5C", "3D");
        log.info(MechanismUtil.getScore(high));
        log.info(MechanismUtil.getScore(low));
        assertTrue(MechanismUtil.getScore(high) > MechanismUtil.getScore(low));
    }

    @Test
    public void onePair_kicker() {
        List<PokerCard> high = of("8C", "8D", "AH", "KC", "QD", "JC", "9H");
        List<PokerCard> low  = of("8C", "8D", "KH", "QC", "JD", "9H", "7C");
        log.info(MechanismUtil.getScore(high));
        log.info(MechanismUtil.getScore(low));
        assertTrue(MechanismUtil.getScore(high) > MechanismUtil.getScore(low));
    }

    @Test
    public void twoPairs_kicker() {
        List<PokerCard> high = of("AC", "AD", "KH", "KS", "QD", "JC", "9H");
        List<PokerCard> low  = of("AC", "AD", "KH", "KS", "JD", "9H", "8C");
        log.info(MechanismUtil.getScore(high));
        log.info(MechanismUtil.getScore(low));
        assertTrue(MechanismUtil.getScore(high) > MechanismUtil.getScore(low));
    }

    @Test
    public void special_1() {
        List<PokerCard> high = of("QC", "5S", "JD", "AS", "KH", "10S", "10C");
        List<PokerCard> low  = of("QC", "5S", "JD", "AS", "KH", "KC", "JS");
        log.info(MechanismUtil.getScore(high));
        log.info(MechanismUtil.getScore(low));
        assertTrue(MechanismUtil.getScore(high) > MechanismUtil.getScore(low));
    }

    @Test
    public void special_2() {
        List<PokerCard> high = of("5C", "4S", "3D", "2S", "AH", "10S", "10C");
        List<PokerCard> low  = of("QC", "5S", "JD", "AS", "KH", "KC", "JS");
        log.info(MechanismUtil.getScore(high));
        log.info(MechanismUtil.getScore(low));
        assertTrue(MechanismUtil.getScore(high) > MechanismUtil.getScore(low));
    }
}