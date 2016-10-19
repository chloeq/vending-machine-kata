package ql.kata.vending.machine;


import java.math.BigDecimal;

public class VendingMachine {


    public BigDecimal acceptCoins(Coin coin) {
        return coin.getValue();
    }
}
