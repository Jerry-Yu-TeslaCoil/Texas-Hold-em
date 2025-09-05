package table.card;

public enum Rank {
    ACE(1, 'A'),
    TWO(2, '2'),
    THREE(3, '3'),
    FOUR(4, '4'),
    FIVE(5, '5'),
    SIX(6, '6'),
    SEVEN(7, '7'),
    EIGHT(8, '8'),
    NINE(9, '9'),
    TEN(10, 'T'),
    JACK(11, 'J'),
    QUEEN(12, 'Q'),
    KING(13, 'K'),
    BLACK_JOKER(14, 'B'),
    RED_JOKER(15, 'R');

    private final int value;
    private final char symbol;

    Rank(int value, char symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    // 可选：提供从值到枚举的查找方法
    public static Rank fromValue(int value) {
        for (Rank rank : values()) {
            if (rank.value == value) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Invalid rank value: " + value);
    }

    // 可选：提供从符号到枚举的查找方法
    public static Rank fromSymbol(char symbol) {
        for (Rank rank : values()) {
            if (rank.symbol == symbol) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Invalid rank symbol: " + symbol);
    }
}
