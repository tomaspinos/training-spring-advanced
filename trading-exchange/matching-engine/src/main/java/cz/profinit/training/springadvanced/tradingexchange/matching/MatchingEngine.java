package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;

/**
 * Matching engine performs order settlement.
 */
public interface MatchingEngine {

    SettlementResult settleBuyOrder(Order order);

    SettlementResult settleSellOrder(Order order);
}
