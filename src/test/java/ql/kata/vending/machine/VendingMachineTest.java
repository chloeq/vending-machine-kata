package ql.kata.vending.machine;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ql.kata.vending.machine.enums.Coin;
import ql.kata.vending.machine.handlers.CoinHandler;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class VendingMachineTest {
    @Mock
    private CoinHandler coinHandler;

    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vendingMachine = new VendingMachine(coinHandler);
    }

    @Test
    public void acceptCoinIfValidReturnFaceValueOtherwiseReturnZero() {
        assertEquals("quarter is accepted with return value 0.25", 0, vendingMachine.acceptCoin(Coin.QUARTER).compareTo(new BigDecimal("0.25")));
        assertEquals("dime is accepted with return value 0.10", 0, vendingMachine.acceptCoin(Coin.DIME).compareTo(new BigDecimal("0.10")));
        assertEquals("nickel is accepted with return value 0.05", 0, vendingMachine.acceptCoin(Coin.NICKEL).compareTo(new BigDecimal("0.05")));
        assertEquals("if other coin is encountered we return 0", 0, vendingMachine.acceptCoin(Coin.PENNY).compareTo(BigDecimal.ZERO));
        assertEquals("if other coin is encountered we return 0", 0, vendingMachine.acceptCoin(Coin.OTHER).compareTo(BigDecimal.ZERO));
    }

    @Test
    public void acceptCoinWillUpdateCurrentDisplayMessageToBalanceAmount() {
        assertTrue("Waiting vending machine message is INSERT COIN", vendingMachine.getCurrentMessage().equals("INSERT COIN"));
        vendingMachine.acceptCoin(Coin.PENNY);
        assertTrue("Insert invalid coin message is unchanged - INSERT COIN", vendingMachine.getCurrentMessage().equals("INSERT COIN"));
        vendingMachine.acceptCoin(Coin.QUARTER);
        assertTrue("Insert one quarter the message says 0.25", vendingMachine.getCurrentMessage().equals("0.25"));
        vendingMachine.acceptCoin(Coin.OTHER);
        assertTrue("Insert invalid coin message is unchanged - 0.25", vendingMachine.getCurrentMessage().equals("0.25"));
        vendingMachine.acceptCoin(Coin.DIME);
        assertTrue("Insert one dime the message says 0.35", vendingMachine.getCurrentMessage().equals("0.35"));
    }

    @Test
    public void acceptCoinWillCallTheRightCoinHandler() {
        vendingMachine.acceptCoin(Coin.PENNY);
        verify(coinHandler, times(1)).rejectCoin(Coin.PENNY);
        vendingMachine.acceptCoin(Coin.OTHER);
        verify(coinHandler, times(1)).rejectCoin(Coin.OTHER);
        vendingMachine.acceptCoin(Coin.NICKEL);
        verify(coinHandler, times(1)).rejectCoin(Coin.NICKEL);
        vendingMachine.acceptCoin(Coin.DIME);
        verify(coinHandler, times(1)).rejectCoin(Coin.DIME);
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(coinHandler, times(1)).collectCoin(Coin.QUARTER);
    }
}