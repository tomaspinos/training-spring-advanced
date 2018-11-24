package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.CurrencyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
public class SellOrderRequestTo implements Serializable {

    private final CurrencyTo requestedCurrency;
    private final CurrencyTo offeredCurrency;
    private final MoneyTo orderAmount;
    private final MoneyTo priceLimit;
    private final Username whoPosted;

    public static SellOrderRequestTo of(CurrencyTo requestedCurrencyCode,
                                        CurrencyTo offeredCurrencyCode,
                                        BigDecimal amount,
                                        BigDecimal minPrice,
                                        Username whoPosted) {

        return new SellOrderRequestTo(
                requestedCurrencyCode,
                offeredCurrencyCode,
                MoneyTo.of(offeredCurrencyCode, amount),
                MoneyTo.of(requestedCurrencyCode, minPrice),
                whoPosted);
    }
}
