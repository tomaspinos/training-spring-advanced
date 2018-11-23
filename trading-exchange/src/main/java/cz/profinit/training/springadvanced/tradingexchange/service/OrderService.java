package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;

public interface OrderService {

    OrderStatusTo createBuyOrder(BuyOrderRequestTo request);

    OrderStatusTo createSellOrder(SellOrderRequestTo request);

    OrderStatusTo getOrderStatus(OrderId id);
}
