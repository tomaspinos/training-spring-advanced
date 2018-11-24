package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@RequiredArgsConstructor
@Slf4j
class MatchingEngineImpl implements MatchingEngine {

    private final OrderQueries orderQueries;

    @Override
    public SettlementResult settleBuyOrder(Order order) {
        log.debug("Starting buy order settlement: {}", order);

        SettlementResult.SettlementResultBuilder settlementResultBuilder = SettlementResult.builder()
                .order(order);

        UserBalanceChanges userBalanceChanges = new UserBalanceChanges();

        Iterator<Order> candidateSellOrders = orderQueries.getCandidateSellOrders(order.getRequestedCurrency(), order.getOfferedCurrency(), order.getPriceLimit())
                .iterator();

        while (order.getRemainingAmount().isGreaterThanZero() && candidateSellOrders.hasNext()) {
            Order matchedOrder = candidateSellOrders.next();
            log.debug("Matched order: {}", matchedOrder);

            Money boughtAmount = order.getRemainingAmount().min(matchedOrder.getRemainingAmount());
            Money paidAmount = Money.of(order.getOfferedCurrency(), matchedOrder.getPriceLimit().getAmount().multiply(boughtAmount.getAmount()));

            order.getRemainingAmount().minus(boughtAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);

            matchedOrder.getRemainingAmount().minus(boughtAmount);
            if (matchedOrder.getRemainingAmount().isZero()) {
                matchedOrder.setSettlementState(OrderSettlementState.SETTLED);
            } else {
                matchedOrder.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);
            }

            Trade trade = Trade.builder()
                    .buyOrder(order)
                    .sellOrder(matchedOrder)
                    .amount(boughtAmount)
                    .price(matchedOrder.getPriceLimit())
                    .build();

            settlementResultBuilder.matchedOrder(matchedOrder).trade(trade);

            userBalanceChanges.add(order.getWhoPosted(), boughtAmount);
            userBalanceChanges.add(order.getWhoPosted(), paidAmount.getNegative());
            userBalanceChanges.add(matchedOrder.getWhoPosted(), boughtAmount.getNegative());
            userBalanceChanges.add(matchedOrder.getWhoPosted(), paidAmount);
        }

        if (order.getRemainingAmount().isZero()) {
            order.setSettlementState(OrderSettlementState.SETTLED);
        }

        return settlementResultBuilder.userBalanceChanges(userBalanceChanges).build();
    }

    @Override
    public SettlementResult settleSellOrder(Order order) {
        log.debug("Starting sell order settlement: {}", order);

        SettlementResult.SettlementResultBuilder settlementResultBuilder = SettlementResult.builder()
                .order(order);

        UserBalanceChanges userBalanceChanges = new UserBalanceChanges();

        // example - sell 100 EUR for 26 CZK - CZK/EUR
        // looking for buy orders that want EUR/CZK
        Iterator<Order> candidateBuyOrders = orderQueries.getCandidateBuyOrders(order.getOfferedCurrency(), order.getRequestedCurrency(), order.getPriceLimit())
                .iterator();

        while (order.getRemainingAmount().isGreaterThanZero() && candidateBuyOrders.hasNext()) {
            Order matchedOrder = candidateBuyOrders.next();
            log.debug("Matched order: {}", matchedOrder);

            Money soldAmount = order.getRemainingAmount().min(matchedOrder.getRemainingAmount());
            Money receivedAmount = Money.of(order.getRequestedCurrency(), matchedOrder.getPriceLimit().getAmount().multiply(soldAmount.getAmount()));

            order.getRemainingAmount().minus(soldAmount);
            order.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);

            matchedOrder.getRemainingAmount().minus(soldAmount);
            if (matchedOrder.getRemainingAmount().isZero()) {
                matchedOrder.setSettlementState(OrderSettlementState.SETTLED);
            } else {
                matchedOrder.setSettlementState(OrderSettlementState.PARTIALLY_SETTLED);
            }

            Trade trade = Trade.builder()
                    .buyOrder(matchedOrder)
                    .sellOrder(order)
                    .amount(soldAmount)
                    .price(matchedOrder.getPriceLimit())
                    .build();

            settlementResultBuilder.matchedOrder(matchedOrder).trade(trade);

            userBalanceChanges.add(order.getWhoPosted(), soldAmount.getNegative());
            userBalanceChanges.add(order.getWhoPosted(), receivedAmount);
            userBalanceChanges.add(matchedOrder.getWhoPosted(), soldAmount);
            userBalanceChanges.add(matchedOrder.getWhoPosted(), receivedAmount.getNegative());
        }

        if (order.getRemainingAmount().isZero()) {
            order.setSettlementState(OrderSettlementState.SETTLED);
        }

        return settlementResultBuilder.userBalanceChanges(userBalanceChanges).build();
    }
}
