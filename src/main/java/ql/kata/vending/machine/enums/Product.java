package ql.kata.vending.machine.enums;


import java.math.BigDecimal;

public enum Product {
    COLA(new BigDecimal("1.00")),
    CHIPS(new BigDecimal("0.50")),
    CANDY(new BigDecimal("0.65"));

    private BigDecimal price;

    Product(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
