package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;

import java.util.List;

public interface OrderQueries {

    List<Order> getCandidateBuyOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit);

    List<Order> getCandidateSellOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit);
}
