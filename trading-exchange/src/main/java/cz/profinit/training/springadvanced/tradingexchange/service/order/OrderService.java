package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;

import java.util.Optional;

public interface OrderService {

    OrderStatusTo createBuyOrder(BuyOrderRequestTo request);

    OrderStatusTo createSellOrder(SellOrderRequestTo request);

    Optional<OrderStatusTo> getOrderStatus(OrderId id);
}
