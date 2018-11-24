package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SettlementResult {

    private final Order order;

    @Singular
    private final List<Order> matchedOrders;

    @Singular
    private final List<Trade> trades;
}
