package ql.kata.vending.machine.handlers;


import ql.kata.vending.machine.enums.Coin;
import ql.kata.vending.machine.enums.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductHandler {

    private Map<String, Integer> productStock;

    public ProductHandler() {
        productStock = new HashMap<>();
    }

    public boolean isProductOutOfStock(final Product product) {
        return false;
    }

    public void dispenseProduct(final Product product) {

    }
}
