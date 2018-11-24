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
            Order matchedOrder = candidateSellOrders.next();

            Money minAmount = MoneyMath.min(order.getRemainingAmount(), matchedOrder.getRemainingAmount());

            MoneyMath.minus(order.getRemainingAmount(), minAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);

            MoneyMath.minus(matchedOrder.getRemainingAmount(), minAmount);
            if (MoneyMath.isZero(matchedOrder.getRemainingAmount())) {
                matchedOrder.setSettlementState(OrderSettlementState.SETTLED);
            } else {
                matchedOrder.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);
            }

            Trade trade = Trade.builder()
                    .buyOrder(order)
                    .sellOrder(matchedOrder)
                    .amount(minAmount)
                    .price(matchedOrder.getPriceLimit())
                    .build();

            settlementResultBuilder.matchedOrder(matchedOrder).trade(trade);
        }

        if (MoneyMath.isZero(order.getRemainingAmount())) {
            order.setSettlementState(OrderSettlementState.SETTLED);
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
            Order matchedOrder = candidateBuyOrders.next();

            Money minAmount = MoneyMath.min(order.getRemainingAmount(), matchedOrder.getRemainingAmount());

            MoneyMath.minus(order.getRemainingAmount(), minAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);

            MoneyMath.minus(matchedOrder.getRemainingAmount(), minAmount);
            if (MoneyMath.isZero(matchedOrder.getRemainingAmount())) {
                matchedOrder.setSettlementState(OrderSettlementState.SETTLED);
            } else {
                matchedOrder.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);
            }

            Trade trade = Trade.builder()
                    .buyOrder(matchedOrder)
                    .sellOrder(order)
                    .amount(minAmount)
                    .price(matchedOrder.getPriceLimit())
                    .build();

            settlementResultBuilder.matchedOrder(matchedOrder).trade(trade);
        }

        if (MoneyMath.isZero(order.getRemainingAmount())) {
            order.setSettlementState(OrderSettlementState.SETTLED);
        }

        return settlementResultBuilder.build();
    }
}
