package pojo.pot;

import pojo.PlayerCoil;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Pot {
    private BigDecimal leastStack;
    private ArrayList<PotStack> stacks;
    private PlayerCoil players;

    public void setLeastStack(BigDecimal leastStack) {
        this.leastStack = leastStack;
    }

    public Pot(BigDecimal leastStack) {
        this.leastStack = leastStack;
        this.stacks = new ArrayList<>();
        this.stacks.add(new PotStack());
    }

    private static class PotStack {
        private BigDecimal amount;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
