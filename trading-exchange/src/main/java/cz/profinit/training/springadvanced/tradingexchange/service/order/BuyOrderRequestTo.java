package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.CurrencyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
public class BuyOrderRequestTo implements Serializable {

    private final CurrencyTo requestedCurrency;
    private final CurrencyTo offeredCurrency;
    private final MoneyTo orderAmount;
    private final MoneyTo priceLimit;
    private final Username whoPosted;

    public static BuyOrderRequestTo of(CurrencyTo requestedCurrencyCode,
                                       CurrencyTo offeredCurrencyCode,
                                       BigDecimal amount,
                                       BigDecimal maxPrice,
                                       Username whoPosted) {

        return new BuyOrderRequestTo(
                requestedCurrencyCode,
                offeredCurrencyCode,
                MoneyTo.of(requestedCurrencyCode, amount),
                MoneyTo.of(offeredCurrencyCode, maxPrice),
                whoPosted);
    }
}
