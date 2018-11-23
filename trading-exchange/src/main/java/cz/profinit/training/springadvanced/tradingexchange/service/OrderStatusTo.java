package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import lombok.Value;

import java.io.Serializable;

@Value
public class OrderStatusTo implements Serializable {

    private final OrderId id;
    private final OrderType type;
    private final OrderSettlementState settlementState;
    private final CurrencyPairTo currencyPair;
    private final MoneyTo requestedAmount;
    private final MoneyTo remainingAmount;
    private final MoneyTo priceLimit;
    private final UserTo whoPosted;

    public static OrderStatusTo fromEntity(Order order) {
        return new OrderStatusTo(
                order.getId(),
                order.getType(),
                order.getSettlementState(),
                CurrencyPairTo.fromEntity(order.getCurrencyPair()),
                MoneyTo.fromEntity(order.getRequestedAmount()),
                MoneyTo.fromEntity(order.getRemainingAmount()),
                MoneyTo.fromEntity(order.getPriceLimit()),
                UserTo.fromEntity(order.getWhoPosted()));
    }
}
