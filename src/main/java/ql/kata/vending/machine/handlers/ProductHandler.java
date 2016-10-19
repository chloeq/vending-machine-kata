package ql.kata.vending.machine.handlers;


import ql.kata.vending.machine.enums.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductHandler {

    private Map<String, Integer> productStock;

    ProductHandler() {
        this.productStock = new HashMap<>();
    }

    public boolean isProductOutOfStock(final Product product) {
        return false;
    }

    public void dispenseProduct(final Product product) {

    }
}
