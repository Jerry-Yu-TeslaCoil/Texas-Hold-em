package pojo;

public class TableMemberResult {

    private final boolean result;
    private final String message;

    public TableMemberResult() {
        this.result = true;
        this.message = "";
    }

    public TableMemberResult(boolean result) {
        this.result = result;
        this.message = "";
    }

    public TableMemberResult(boolean result, String message) {
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
