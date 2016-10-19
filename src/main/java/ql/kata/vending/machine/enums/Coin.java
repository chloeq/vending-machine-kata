package ql.kata.vending.machine.enums;


import java.math.BigDecimal;

public enum Coin {

    NICKEL("nickel", new BigDecimal("0.05")),
    DIME("dime", new BigDecimal("0.10")),
    QUARTER("quarter", new BigDecimal("0.25")),
    PENNY("penny", BigDecimal.ZERO),
    OTHER("other", BigDecimal.ZERO);

    private String name;
    private BigDecimal value;

    Coin(final String name, final BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public boolean isValid() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }
}
