package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
class MatchingEngineImpl implements MatchingEngine {

    private final OrderQueries orderQueries;

    @Override
    public SettlementResult settleBuyOrder(Order order) {
        SettlementResult.SettlementResultBuilder settlementResultBuilder = SettlementResult.builder()
                .order(order);

        Iterator<Order> candidateSellOrders = orderQueries.getCandidateSellOrders(order.getRequestedCurrency(), order.getOfferedCurrency(), order.getPriceLimit())
                .iterator();

        while (MoneyMath.isGreaterThanZero(order.getRemainingAmount()) && candidateSellOrders.hasNext()) {
            Order sellOrder = candidateSellOrders.next();

            Money minAmount = MoneyMath.min(order.getRemainingAmount(), sellOrder.getRemainingAmount());

            MoneyMath.minus(order.getRemainingAmount(), minAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_FILLED);

            MoneyMath.minus(sellOrder.getRemainingAmount(), minAmount);
            if (MoneyMath.isZero(sellOrder.getRemainingAmount())) {
                sellOrder.setSettlementState(OrderSettlementState.FILLED);
            } else {
                sellOrder.setSettlementState(OrderSettlementState.PARTIALLY_FILLED);
            }

            Trade trade = Trade.builder()
                    .buyOrder(order)
                    .sellOrder(sellOrder)
                    .amount(minAmount)
                    .price(sellOrder.getPriceLimit())
                    .build();

            settlementResultBuilder.matchedOrder(sellOrder).trade(trade);
        }

        if (MoneyMath.isZero(order.getRemainingAmount())) {
            order.setSettlementState(OrderSettlementState.FILLED);
        }

        return settlementResultBuilder.build();
    }

    @Override
    public SettlementResult settleSellOrder(Order order) {
        SettlementResult.SettlementResultBuilder settlementResultBuilder = SettlementResult.builder()
                .order(order);

        Iterator<Order> candidateBuyOrders = orderQueries.getCandidateBuyOrders(order.getRequestedCurrency(), order.getOfferedCurrency(), order.getPriceLimit())
                .iterator();

        while (MoneyMath.isGreaterThanZero(order.getRemainingAmount()) && candidateBuyOrders.hasNext()) {
            Order buyOrder = candidateBuyOrders.next();

            Money minAmount = MoneyMath.min(order.getRemainingAmount(), buyOrder.getRemainingAmount());

            MoneyMath.minus(order.getRemainingAmount(), minAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_FILLED);

            MoneyMath.minus(buyOrder.getRemainingAmount(), minAmount);
            if (MoneyMath.isZero(buyOrder.getRemainingAmount())) {
                buyOrder.setSettlementState(OrderSettlementState.FILLED);
            } else {
                buyOrder.setSettlementState(OrderSettlementState.PARTIALLY_FILLED);
            }

            Trade trade = Trade.builder()
                    .buyOrder(buyOrder)
                    .sellOrder(order)
                    .amount(minAmount)
                    .price(buyOrder.getPriceLimit())
                    .build();

            settlementResultBuilder.matchedOrder(buyOrder).trade(trade);
        }

        if (MoneyMath.isZero(order.getRemainingAmount())) {
            order.setSettlementState(OrderSettlementState.FILLED);
        }

        return settlementResultBuilder.build();
    }
}
