package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;

public interface MatchingEngine {

    SettlementResult settleBuyOrder(Order order);

    SettlementResult settleSellOrder(Order order);
}
