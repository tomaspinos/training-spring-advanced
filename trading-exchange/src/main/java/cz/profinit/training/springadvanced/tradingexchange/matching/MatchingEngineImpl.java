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

        while (order.getRemainingAmount().isGreaterThanZero() && candidateSellOrders.hasNext()) {
            Order matchedOrder = candidateSellOrders.next();

            Money minAmount = order.getRemainingAmount().min(matchedOrder.getRemainingAmount());

            order.getRemainingAmount().minus(minAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);

            matchedOrder.getRemainingAmount().minus(minAmount);
            if (matchedOrder.getRemainingAmount().isZero()) {
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

        if (order.getRemainingAmount().isZero()) {
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

        while (order.getRemainingAmount().isGreaterThanZero() && candidateBuyOrders.hasNext()) {
            Order matchedOrder = candidateBuyOrders.next();

            Money minAmount = order.getRemainingAmount().min(matchedOrder.getRemainingAmount());

            order.getRemainingAmount().minus(minAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);

            matchedOrder.getRemainingAmount().minus(minAmount);
            if (matchedOrder.getRemainingAmount().isZero()) {
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

        if (order.getRemainingAmount().isZero()) {
            order.setSettlementState(OrderSettlementState.SETTLED);
        }

        return settlementResultBuilder.build();
    }
}
