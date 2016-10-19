package ql.kata.vending.machine.handlers;


import ql.kata.vending.machine.enums.Coin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CoinHandler {

    private Map<String, Integer> coinBank;

    CoinHandler() {
        coinBank = new HashMap<>();
    }

    public void collectCoin(final Coin coin) {
        if (coinBank.containsKey(coin.getName())) {
            coinBank.put(coin.getName(), coinBank.get(coin.getName())+1);
        } else {
            coinBank.put(coin.getName(), 1);
        }
    }

    public void rejectCoin(final Coin coin) {

    }
    public void placeToCoinReturn(final BigDecimal amount) {

    }

    private int getCoinCount(final Coin coin) {
        int count = 0;
        if(coinBank.containsKey(coin.getName()) && coinBank.get(coin.getName()) > 0) {
            count = coinBank.get(coin.getName());
        }
        return count;
    }

}
