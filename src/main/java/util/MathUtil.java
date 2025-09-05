package util;

public class MathUtil {
    public static int normalizeIndex(int position, int size) {
        if (size == -1) return 0;
        return (position % size + size) % size;
    }
}
