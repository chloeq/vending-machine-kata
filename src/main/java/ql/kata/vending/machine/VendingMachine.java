package ql.kata.vending.machine;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    private String currentMessage;
    private BigDecimal currentBalance;
    private Map<Coin, Integer> coinBank;

    VendingMachine() {
        currentMessage = "INSERT COIN";
        currentBalance = new BigDecimal("0");
        coinBank = new HashMap<>();
    }

    public BigDecimal acceptCoin(Coin coin) {
        if (coin.isValid()) {
            currentBalance = currentBalance.add(coin.getValue());
            currentMessage = currentBalance.toString();
            collectCoin(coin);
        } else {
            rejectCoin();
        }
        return coin.getValue();
    }

    public String getCurrentMessage() {
        return currentMessage;
    }
    private void collectCoin(Coin coin) {
        if (coinBank.containsKey(coin)) {
            coinBank.put(coin, coinBank.get(coin)+1);
        } else {
            coinBank.put(coin, 1);
        }
    }

    private void rejectCoin() {

    }
}
