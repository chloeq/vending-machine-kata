package ql.kata.vending.machine;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class VendingMachineTest {



    @Test
    public void acceptCoinsIfValidReturnFaceValueOtherwiseReturnZero() {
        VendingMachine vendingMachine = new VendingMachine();
        assertEquals("quarter is accepted with return value 0.25", 0, vendingMachine.acceptCoins(Coin.QUARTER).compareTo(new BigDecimal("0.25")));
        assertEquals("dime is accepted with return value 0.10", 0, vendingMachine.acceptCoins(Coin.DIME).compareTo(new BigDecimal("0.10")));
        assertEquals("nickel is accepted with return value 0.05", 0, vendingMachine.acceptCoins(Coin.NICKEL).compareTo(new BigDecimal("0.05")));
        assertEquals("if other coin is encountered we return 0", 0, vendingMachine.acceptCoins(Coin.OTHER).compareTo(BigDecimal.ZERO));
    }

}