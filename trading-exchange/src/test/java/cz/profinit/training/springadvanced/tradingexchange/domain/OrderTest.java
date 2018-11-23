package cz.profinit.training.springadvanced.tradingexchange.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class OrderTest {

    private static final Currency EUR = Currency.builder().id(1L).code("EUR").build();
    private static final Currency CZK = Currency.builder().id(2L).code("CZK").build();

    @Test
    public void testBuyEURForCZKIsValid() {
        Order order = Order.builder()
                .type(OrderType.BUY)
                .requestedCurrency(EUR)
                .offeredCurrency(CZK)
                .orderAmount(Money.builder().amount(new BigDecimal(100)).currency(EUR).build())
                .remainingAmount(Money.builder().amount(new BigDecimal(100)).currency(EUR).build())
                .priceLimit(Money.builder().amount(new BigDecimal(25)).currency(CZK).build())
                .build();

        assertTrue(order.isValid());
    }

    @Test
    public void testSellEURForCZKIsValid() {
        Order order = Order.builder()
                .type(OrderType.SELL)
                .requestedCurrency(CZK)
                .offeredCurrency(EUR)
                .orderAmount(Money.builder().amount(new BigDecimal(100)).currency(EUR).build())
                .remainingAmount(Money.builder().amount(new BigDecimal(100)).currency(EUR).build())
                .priceLimit(Money.builder().amount(new BigDecimal(25)).currency(CZK).build())
                .build();

        assertTrue(order.isValid());
    }
}