package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import cz.profinit.training.springadvanced.tradingexchange.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
class OrderQueriesImpl implements OrderQueries {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Order> getCandidateBuyOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit) {
        return orderRepository.findByTypeAndRequestedCurrencyAndOfferedCurrencyAndPriceLimitAmountGreaterThanEqual(
                OrderType.BUY, requestedCurrency, offeredCurrency, priceLimit.getAmount());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getCandidateSellOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit) {
        return orderRepository.findByTypeAndRequestedCurrencyAndOfferedCurrencyAndPriceLimitAmountLessThanEqual(
                OrderType.SELL, requestedCurrency, offeredCurrency, priceLimit.getAmount());
    }
}
