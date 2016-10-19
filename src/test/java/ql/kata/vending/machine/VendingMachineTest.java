package ql.kata.vending.machine;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ql.kata.vending.machine.enums.Coin;
import ql.kata.vending.machine.enums.Product;
import ql.kata.vending.machine.handlers.CoinHandler;
import ql.kata.vending.machine.handlers.DisplayHandler;
import ql.kata.vending.machine.handlers.ProductHandler;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class VendingMachineTest {
    @Mock
    private CoinHandler coinHandler;

    @Mock
    private ProductHandler productHandler;

    @Mock
    private DisplayHandler displayHandler;

    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vendingMachine = new VendingMachine(coinHandler, productHandler, displayHandler);
    }

    @Test
    public void acceptCoinWillUpdateCurrentDisplayMessageToBalanceAmount() {
        verify(displayHandler, times(1)).displayMessage("INSERT COIN");
        vendingMachine.acceptCoin(Coin.PENNY);
        verify(displayHandler, times(2)).displayMessage("INSERT COIN");
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(displayHandler, times(1)).displayMessage("0.25");
        vendingMachine.acceptCoin(Coin.OTHER);
        verify(displayHandler, times(2)).displayMessage("0.25");
        vendingMachine.acceptCoin(Coin.DIME);
        verify(displayHandler, times(1)).displayMessage("0.35");
    }

    @Test
    public void acceptCoinWillCallTheRightCoinHandler() {
        vendingMachine.acceptCoin(Coin.PENNY);
        verify(coinHandler, times(1)).rejectCoin(Coin.PENNY);
        vendingMachine.acceptCoin(Coin.OTHER);
        verify(coinHandler, times(1)).rejectCoin(Coin.OTHER);
        vendingMachine.acceptCoin(Coin.NICKEL);
        verify(coinHandler, times(1)).collectCoin(Coin.NICKEL);
        vendingMachine.acceptCoin(Coin.DIME);
        verify(coinHandler, times(1)).collectCoin(Coin.DIME);
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(coinHandler, times(1)).collectCoin(Coin.QUARTER);
    }

    @Test
    public void selectProductWithNoMoney() {
        verify(displayHandler, times(1)).displayMessage("INSERT COIN");
        vendingMachine.selectProduct(Product.COLA);
        verify(productHandler, never()).dispenseProduct(Product.COLA);
        verify(displayHandler, times(1)).displayMessage("PRICE 1.00");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(2)).displayMessage("INSERT COIN");
    }

    @Test
    public void selectProductWithNotEnoughMoney() {
        verify(displayHandler, times(1)).displayMessage("INSERT COIN");
        vendingMachine.acceptCoin(Coin.NICKEL);
        verify(displayHandler, times(1)).displayMessage("0.05");
        vendingMachine.selectProduct(Product.CANDY);
        verify(productHandler, never()).dispenseProduct(Product.CANDY);
        verify(displayHandler, times(1)).displayMessage("PRICE 0.65");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(2)).displayMessage("0.05");
    }

    @Test
    public void selectProductWithEnoughMoney() {
        verify(displayHandler, times(1)).displayMessage("INSERT COIN");
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(displayHandler, times(1)).displayMessage("0.25");
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(displayHandler, times(1)).displayMessage("0.50");
        vendingMachine.selectProduct(Product.CHIPS);
        verify(productHandler, times(1)).dispenseProduct(Product.CHIPS);
        verify(displayHandler, times(1)).displayMessage("THANK YOU");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(2)).displayMessage("INSERT COIN");
        assertEquals("current balance is set back to 0.0", 0, vendingMachine.getCurrentBalance().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void selectProductWillMakeChangeWithCorrectAmount() {
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.selectProduct(Product.CANDY);
        verify(coinHandler, times(1)).placeToCoinReturn(new BigDecimal("0.10"));
        assertEquals("current balance is set back to 0.0", 0, vendingMachine.getCurrentBalance().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void returnCoinsWillReturnFullAmountAndResetMessage() {
        verify(displayHandler, times(1)).displayMessage("INSERT COIN");
        vendingMachine.acceptCoin(Coin.DIME);
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(displayHandler, times(1)).displayMessage("0.35");
        vendingMachine.returnCoins();
        verify(coinHandler, times(1)).placeToCoinReturn(new BigDecimal("0.35"));
        verify(displayHandler, times(2)).displayMessage("INSERT COIN");
        assertEquals("Balance after coin return is 0.0", 0, vendingMachine.getCurrentBalance().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void checkForOutOfStockAndDisplayCorrectInfo() {
        when(productHandler.isProductOutOfStock(Product.CHIPS)).thenReturn(true);
        verify(displayHandler, times(1)).displayMessage("INSERT COIN");
        vendingMachine.selectProduct(Product.CHIPS);
        verify(displayHandler, times(1)).displayMessage("SOLD OUT");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(2)).displayMessage("INSERT COIN");
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.acceptCoin(Coin.QUARTER);
        verify(displayHandler, times(1)).displayMessage("0.50");
        vendingMachine.selectProduct(Product.CHIPS);
        verify(displayHandler, times(2)).displayMessage("SOLD OUT");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(2)).displayMessage("0.50");
    }

    @Test
    public void checkForExactChangeAndDisplayCorrectInfo() {
        when(coinHandler.notAbleToMakeChange()).thenReturn(true);
        when(productHandler.isProductOutOfStock(Product.CANDY)).thenReturn(true);
        vendingMachine.selectProduct(Product.CANDY);
        verify(displayHandler, times(1)).displayMessage("SOLD OUT");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(1)).displayMessage("EXACT CHANGE ONLY");
        vendingMachine.acceptCoin(Coin.DIME);
        verify(displayHandler, times(1)).displayMessage("0.10");
        vendingMachine.returnCoins();
        verify(displayHandler, times(2)).displayMessage("EXACT CHANGE ONLY");
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.acceptCoin(Coin.QUARTER);
        vendingMachine.selectProduct(Product.CHIPS);
        verify(displayHandler, times(1)).displayMessage("THANK YOU");
        vendingMachine.checkDisplay();
        verify(displayHandler, times(3)).displayMessage("EXACT CHANGE ONLY");
    }
}