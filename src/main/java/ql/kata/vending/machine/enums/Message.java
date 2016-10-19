package ql.kata.vending.machine.enums;


public enum Message {
    WAITING("INSERT COIN"),
    THANKYOU("THANK YOU"),
    PRICE("PRICE"),
    SOLDOUT("SOLD OUT");

    String message;
    Message(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
