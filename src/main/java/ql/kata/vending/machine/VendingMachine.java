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
        currentBalance = BigDecimal.ZERO;
        this.coinHandler = coinHandler;
        this.productHandler = productHandler;
        this.displayHandler = displayHandler;
        sendCurrentMessageToDisplay();
    }

    public void acceptCoin(final Coin coin) {
        if (coin.isValid()) {
            currentBalance = currentBalance.add(coin.getValue());
            currentMessage = currentBalance.toString();
            coinHandler.collectCoin(coin);
        } else {
            coinHandler.rejectCoin(coin);
        }
        sendCurrentMessageToDisplay();
    }

    public void selectProduct(final Product product) {
        if(checkForSoldOut(product)) return;
        BigDecimal price = product.getPrice();
        if (currentBalance.compareTo(price) >= 0) {
            productHandler.dispenseProduct(product);
            currentMessage = Message.THANKYOU.getMessage();
            sendCurrentMessageToDisplay();
            resetCurrentMessage();
            if(currentBalance.compareTo(price) > 0) {
                coinHandler.placeToCoinReturn(currentBalance.subtract(price));
            }
            currentBalance = BigDecimal.ZERO;
        } else {
            currentMessage = String.format("%s %s", Message.PRICE.getMessage(), product.getPrice().toString());
            sendCurrentMessageToDisplay();
            if(hasPositiveBalance()) {
                currentMessage = currentBalance.toString();
            } else {
                resetCurrentMessage();
            }
        }
    }

    private boolean checkForSoldOut(Product product) {
        if(productHandler.isProductOutOfStock(product)){
            currentMessage = Message.SOLDOUT.getMessage();
            sendCurrentMessageToDisplay();
            if(hasPositiveBalance()) {
                currentMessage = currentBalance.toString();
            } else {
                resetCurrentMessage();
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean hasPositiveBalance() {
        return (currentBalance.compareTo(BigDecimal.ZERO) > 0);
    }

    public void returnCoins() {
        coinHandler.placeToCoinReturn(currentBalance);
        currentBalance = BigDecimal.ZERO;
        resetCurrentMessage();
        sendCurrentMessageToDisplay();
    }

    public void checkDisplay() {
        sendCurrentMessageToDisplay();
    }

    private void sendCurrentMessageToDisplay() {
        displayHandler.displayMessage(currentMessage);
    }

    public void resetCurrentMessage() {
        currentMessage = Message.WAITING.getMessage();
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }


}
