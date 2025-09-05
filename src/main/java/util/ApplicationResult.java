package util;

public class ApplicationResult {

    private final boolean result;
    private final String message;

    public static ApplicationResult success() {
        return new ApplicationResult(true, null);
    }

    public static ApplicationResult success(String message) {
        return new ApplicationResult(true, message);
    }

    public static ApplicationResult error(String message) {
        return new ApplicationResult(false, message);
    }

    private ApplicationResult(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
