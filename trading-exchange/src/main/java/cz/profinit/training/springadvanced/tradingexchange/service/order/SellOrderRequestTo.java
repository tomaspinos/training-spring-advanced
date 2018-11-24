package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.CurrencyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.Value;

import java.io.Serializable;

@Value
public class SellOrderRequestTo implements Serializable {

    private final CurrencyTo requestedCurrency;
    private final CurrencyTo offeredCurrency;
    private final MoneyTo orderAmount;
    private final MoneyTo priceLimit;
    private final Username whoPosted;
}
