package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;

public interface OrderService {

    void createBuyOrder();

    void createSellOrder();

    void getOrder(OrderId id);
}
