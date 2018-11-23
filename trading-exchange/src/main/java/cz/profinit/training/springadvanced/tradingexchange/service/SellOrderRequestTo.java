package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.UserId;
import lombok.Value;

import java.io.Serializable;

@Value
public class SellOrderRequestTo implements Serializable {

    private final CurrencyTo requestedCurrency;
    private final CurrencyTo offeredCurrency;
    private final MoneyTo orderAmount;
    private final MoneyTo priceLimit;
    private final UserId whoPosted;
}
