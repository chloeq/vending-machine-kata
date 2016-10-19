package ql.kata.vending.machine.enums;


import java.math.BigDecimal;

public enum Product {
    COLA("cola", new BigDecimal("1.00")),
    CHIPS("chips", new BigDecimal("0.50")),
    CANDY("candy", new BigDecimal("0.65"));

    private String name;
    private BigDecimal price;

    Product(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }
}
