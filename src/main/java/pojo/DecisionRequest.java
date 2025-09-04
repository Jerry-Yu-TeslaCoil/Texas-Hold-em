package pojo;

import java.math.BigDecimal;

public class DecisionRequest {
    private final BigDecimal leastStackRequest;

    public BigDecimal getLeastStackRequest() {
        return leastStackRequest;
    }

    public DecisionRequest(BigDecimal leastStackRequest) {
        this.leastStackRequest = leastStackRequest;
    }
}
