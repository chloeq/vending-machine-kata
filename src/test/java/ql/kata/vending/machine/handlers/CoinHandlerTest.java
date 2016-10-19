package ql.kata.vending.machine.handlers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import ql.kata.vending.machine.VendingMachine;
import ql.kata.vending.machine.enums.Coin;

import static org.junit.Assert.*;


public class CoinHandlerTest {

    private CoinHandler coinHandler;

    @Before
    public void setUp() {
        coinHandler = new CoinHandler();
    }

    @Test
    public void testCollectCoin() {
        coinHandler.collectCoin(Coin.DIME);
        coinHandler.collectCoin(Coin.DIME);
        coinHandler.collectCoin(Coin.QUARTER);
        assertEquals("Collected two dimes", 2, coinHandler.getCoinCount(Coin.DIME));
        assertEquals("Collected one quarter", 1, coinHandler.getCoinCount(Coin.QUARTER));
        assertEquals("Collected zero nickel", 0, coinHandler.getCoinCount(Coin.NICKEL));
    }
}