package ql.kata.vending.machine.handlers;


import ql.kata.vending.machine.enums.Coin;

import java.util.HashMap;
import java.util.Map;

public class CoinHandler {

    private Map<Coin, Integer> coinBank;

    public CoinHandler() {
        coinBank = new HashMap<>();
    }

    public void collectCoin(final Coin coin) {
        if (coinBank.containsKey(coin)) {
            coinBank.put(coin, coinBank.get(coin)+1);
        } else {
            coinBank.put(coin, 1);
        }
    }

    public void rejectCoin(final Coin coin) {

    }

    public int getCoinCount(final Coin coin) {
        int count = 0;
        if(coinBank.containsKey(coin)) {
            count = coinBank.get(coin);
        }
        return count;
    }

}
