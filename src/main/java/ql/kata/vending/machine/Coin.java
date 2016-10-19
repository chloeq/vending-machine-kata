package ql.kata.vending.machine;


import java.math.BigDecimal;

public enum Coin {

    NICKEL(new BigDecimal("0.05")),
    DIME(new BigDecimal("0.10")),
    QUARTER(new BigDecimal("0.25")),
    PENNY(BigDecimal.ZERO),
    OTHER(BigDecimal.ZERO);

    private BigDecimal value;

    Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public boolean isValid() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }
}
