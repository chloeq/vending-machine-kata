package ql.kata.vending.machine;


import ql.kata.vending.machine.enums.Coin;
import ql.kata.vending.machine.enums.Message;
import ql.kata.vending.machine.enums.Product;
import ql.kata.vending.machine.handlers.CoinHandler;
import ql.kata.vending.machine.handlers.DisplayHandler;
import ql.kata.vending.machine.handlers.ProductHandler;

import java.math.BigDecimal;

public class VendingMachine {

    private String currentMessage;
    private BigDecimal currentBalance;
    private CoinHandler coinHandler;
    private ProductHandler productHandler;
    private DisplayHandler displayHandler;



    VendingMachine(final CoinHandler coinHandler,
                   final ProductHandler productHandler,
                   final DisplayHandler displayHandler) {
        currentMessage = Message.WAITING.getMessage();
        currentBalance = new BigDecimal("0");
        this.coinHandler = coinHandler;
        this.productHandler = productHandler;
        this.displayHandler = displayHandler;
    }

    public BigDecimal acceptCoin(final Coin coin) {
        if (coin.isValid()) {
            currentBalance = currentBalance.add(coin.getValue());
            currentMessage = currentBalance.toString();
            coinHandler.collectCoin(coin);
        } else {
            coinHandler.rejectCoin(coin);
        }
        return coin.getValue();
    }

    public void selectProduct(final Product product) {
        BigDecimal price = product.getPrice();
        if (currentBalance.compareTo(price) >= 0) {
            productHandler.dispenseProduct(product);
            currentMessage = Message.THANKYOU.getMessage();
            displayHandler.displayMessage(currentMessage);
            currentMessage = Message.WAITING.getMessage();
            if(currentBalance.compareTo(price) > 0) {
                coinHandler.placeToCoinReturn(currentBalance.subtract(price));
            }
            currentBalance = BigDecimal.ZERO;
        } else {
            currentMessage = String.format("%s %s", Message.PRICE.getMessage(), product.getPrice().toString());
            displayHandler.displayMessage(currentMessage);
            if (currentBalance.compareTo(BigDecimal.ZERO) == 0) {
                currentMessage = Message.WAITING.getMessage();
            } else {
                currentMessage = currentBalance.toString();
            }
        }
    }

    public void checkDisplay() {
        displayHandler.displayMessage(currentMessage);
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

}
