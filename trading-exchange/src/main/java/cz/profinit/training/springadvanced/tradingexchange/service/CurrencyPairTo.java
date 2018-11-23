package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.CurrencyPair;
import lombok.Value;

import java.io.Serializable;

@Value
public class CurrencyPairTo implements Serializable {

    private final CurrencyTo firstCurrency;
    private final CurrencyTo secondCurrency;

    public static CurrencyPairTo fromEntity(CurrencyPair currencyPair) {
        return new CurrencyPairTo(CurrencyTo.fromEntity(currencyPair.getFirstCurrency()), CurrencyTo.fromEntity(currencyPair.getSecondCurrency()));
    }
}
