package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static cz.profinit.training.springadvanced.tradingexchange.domain.SampleData.CZK;
import static cz.profinit.training.springadvanced.tradingexchange.domain.SampleData.EUR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatchingEngineImplTest {

    private MatchingEngine matchingEngine;
    private MockOrderQueries orderQueries;
    private AtomicLong id;

    @Before
    public void setUp() {
        orderQueries = new MockOrderQueries();
        matchingEngine = new MatchingEngineImpl(orderQueries);
        id = new AtomicLong(1);
    }

    @Test
    public void shouldSettleBuyOrderCompletelyWhenMatchingSellOrdersAvailable() {
        // buy 100 EUR for max 26 CZK
        Order buyOrder = Order.buy(id.getAndIncrement(), EUR, CZK, 100, 26);

        // sell 60 EUR for min 24 CZK
        Order firstSellOrder = Order.sell(id.getAndIncrement(), CZK, EUR, 60, 24);
        // sell 40 EUR for min 25 CZK
        Order secondSellOrder = Order.sell(id.getAndIncrement(), CZK, EUR, 40, 25);

        orderQueries.addCandidateSellOrders(firstSellOrder, secondSellOrder);

        SettlementResult settlementResult = matchingEngine.settleBuyOrder(buyOrder);

        assertEquals(OrderSettlementState.SETTLED, settlementResult.getOrder().getSettlementState());
        assertEquals(2, settlementResult.getTrades().size());

        Trade firstTrade = settlementResult.getTrades().get(0);
        assertTrue(firstTrade.matches(buyOrder, firstSellOrder, Money.of(EUR, 60), Money.of(CZK, 24)));

        Trade secondTrade = settlementResult.getTrades().get(1);
        assertTrue(secondTrade.matches(buyOrder, secondSellOrder, Money.of(EUR, 40), Money.of(CZK, 25)));
    }

    @Test
    public void shouldSettleSellOrderCompletelyWhenMatchingBuyOrdersAvailable() {
        // sell 60 EUR for min 24 CZK
        Order sellOrder = Order.sell(id.getAndIncrement(), CZK, EUR, 60, 24);

        // buy 40 EUR for max 25 CZK
        Order firstBuyOrder = Order.buy(id.getAndIncrement(), EUR, CZK, 40, 25);
        // buy 20 EUR for max 26 CZK
        Order secondBuyOrder = Order.buy(id.getAndIncrement(), EUR, CZK, 20, 26);

        orderQueries.addCandidateBuyOrders(firstBuyOrder, secondBuyOrder);

        SettlementResult settlementResult = matchingEngine.settleSellOrder(sellOrder);

        assertEquals(OrderSettlementState.SETTLED, settlementResult.getOrder().getSettlementState());
        assertEquals(2, settlementResult.getTrades().size());

        Trade firstTrade = settlementResult.getTrades().get(0);
        assertTrue(firstTrade.matches(firstBuyOrder, sellOrder, Money.of(EUR, 40), Money.of(CZK, 25)));

        Trade secondTrade = settlementResult.getTrades().get(1);
        assertTrue(secondTrade.matches(secondBuyOrder, sellOrder, Money.of(EUR, 20), Money.of(CZK, 26)));
    }
}