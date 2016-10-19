package ql.kata.vending.machine;


import ql.kata.vending.machine.enums.Coin;
import ql.kata.vending.machine.enums.Message;
import ql.kata.vending.machine.handlers.CoinHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private String currentMessage;
    private BigDecimal currentBalance;
    private CoinHandler coinHandler;


    VendingMachine(final CoinHandler coinHandler) {
        currentMessage = Message.WAITING.getMessage();
        currentBalance = new BigDecimal("0");
        this.coinHandler = coinHandler;
    }

    public BigDecimal acceptCoin(Coin coin) {
        if (coin.isValid()) {
            currentBalance = currentBalance.add(coin.getValue());
            currentMessage = currentBalance.toString();
            coinHandler.collectCoin(coin);
        } else {
            coinHandler.rejectCoin(coin);
        }
        return coin.getValue();
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

}
