package table.card;


/**
 * Enum for the poker ranks.
 *
 * @author jerry
 *
 * @version 1.0
 */
public enum Rank {
    ACE(13, "A"),
    TWO(1, "2"),
    THREE(2, "3"),
    FOUR(3, "4"),
    FIVE(4, "5"),
    SIX(5, "6"),
    SEVEN(6, "7"),
    EIGHT(7, "8"),
    NINE(8, "9"),
    TEN(9, "10"),
    JACK(10, "J"),
    QUEEN(11, "Q"),
    KING(12, "K"),
    BLACK_JOKER(14, "B"),
    RED_JOKER(15, "R");

    private final int value;
    private final String symbol;

    Rank(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    // 可选：提供从值到枚举的查找方法

    /**
     * Retrieve rank by value.
     * @param value The appointed value.
     * @return Corresponding rank.
     */
    public static Rank fromValue(int value) {
        for (Rank rank : values()) {
            if (rank.value == value) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Invalid rank value: " + value);
    }

    /**
     * Retrieve rank by symbol.
     * @param symbol The appointed symbol.
     * @return Corresponding rank.
     */
    public static Rank fromSymbol(String symbol) {
        for (Rank rank : values()) {
            if (rank.symbol.equals(symbol)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Invalid rank symbol: " + symbol);
    }
}
