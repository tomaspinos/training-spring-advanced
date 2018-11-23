package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
class OrderQueriesImpl implements OrderQueries {

    @Override
    public List<Order> getCandidateBuyOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit) {
        return Collections.emptyList();
    }

    @Override
    public List<Order> getCandidateSellOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit) {
        return Collections.emptyList();
    }
}
