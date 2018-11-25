package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import cz.profinit.training.springadvanced.tradingexchange.service.CurrencyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.user.UserTo;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class OrderStatusTo implements Serializable {

    private final OrderId id;
    private final OrderType type;
    private final OrderSettlementState settlementState;
    private final CurrencyTo requestedCurrency;
    private final CurrencyTo offeredCurrency;
    private final MoneyTo orderAmount;
    private final MoneyTo remainingAmount;
    private final MoneyTo priceLimit;
    private final UserTo whoPosted;
    private final LocalDateTime whenCreated;

    public static OrderStatusTo fromEntity(Order order) {
        return new OrderStatusTo(
                order.getId(),
                order.getType(),
                order.getSettlementState(),
                CurrencyTo.fromEntity(order.getRequestedCurrency()),
                CurrencyTo.fromEntity(order.getOfferedCurrency()),
                MoneyTo.fromEntity(order.getOrderAmount()),
                MoneyTo.fromEntity(order.getRemainingAmount()),
                MoneyTo.fromEntity(order.getPriceLimit()),
                UserTo.fromEntity(order.getWhoPosted()),
                order.getWhenCreated());
    }
}
