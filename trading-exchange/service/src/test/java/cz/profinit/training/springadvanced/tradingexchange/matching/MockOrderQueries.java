package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MockOrderQueries implements OrderQueries {

    private List<Order> candidateBuyOrders = new ArrayList<>();
    private List<Order> candidateSellOrders = new ArrayList<>();

    public void addCandidateBuyOrders(Order... candidateBuyOrders) {
        this.candidateBuyOrders.addAll(Arrays.asList(candidateBuyOrders));
    }

    public void addCandidateSellOrders(Order... candidateSellOrders) {
        this.candidateSellOrders.addAll(Arrays.asList(candidateSellOrders));
    }

    @Override
    public List<Order> getCandidateBuyOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit) {
        return candidateBuyOrders;
    }

    @Override
    public List<Order> getCandidateSellOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit) {
        return candidateSellOrders;
    }
}
