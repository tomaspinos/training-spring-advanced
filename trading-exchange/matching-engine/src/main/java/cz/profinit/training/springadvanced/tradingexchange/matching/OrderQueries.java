package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;

import java.util.List;

/**
 * Obtains order required by {@link MatchingEngine} during order settlement.
 */
public interface OrderQueries {

    /**
     * Executed during {@link cz.profinit.training.springadvanced.tradingexchange.domain.OrderType#SELL SELL} order settlement.
     * Searches {@link cz.profinit.training.springadvanced.tradingexchange.domain.OrderType#BUY BUY} orders to match the settled order with.
     *
     * @param requestedCurrency See {@link Order#getRequestedCurrency()}
     * @param offeredCurrency   See {@link Order#getOfferedCurrency()}
     * @param priceLimit        See {@link Order#getPriceLimit()}
     * @return Candidate orders or empty list
     */
    List<Order> getCandidateBuyOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit);

    /**
     * Executed during {@link cz.profinit.training.springadvanced.tradingexchange.domain.OrderType#BUY BUY} order settlement.
     * Searches {@link cz.profinit.training.springadvanced.tradingexchange.domain.OrderType#SELL SELL} orders to match the settled order with.
     *
     * @param requestedCurrency See {@link Order#getRequestedCurrency()}
     * @param offeredCurrency   See {@link Order#getOfferedCurrency()}
     * @param priceLimit        See {@link Order#getPriceLimit()}
     * @return Candidate orders or empty list
     */
    List<Order> getCandidateSellOrders(Currency requestedCurrency, Currency offeredCurrency, Money priceLimit);
}
