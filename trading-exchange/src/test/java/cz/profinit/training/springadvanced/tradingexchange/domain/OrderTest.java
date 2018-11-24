package cz.profinit.training.springadvanced.tradingexchange.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class OrderTest {

    @Test
    public void testBuyEURForCZKIsValid() {
        assertTrue(Order.buy(1, SampleData.EUR, SampleData.CZK, 100, 25, SampleData.USER_A).isValid());
    }

    @Test
    public void testSellEURForCZKIsValid() {
        assertTrue(Order.sell(1, SampleData.CZK, SampleData.EUR, 100, 25, SampleData.USER_A).isValid());
    }
}