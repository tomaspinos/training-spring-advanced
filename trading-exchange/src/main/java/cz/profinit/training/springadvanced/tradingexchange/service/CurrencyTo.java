package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import lombok.Value;

@Value(staticConstructor = "of")
public class CurrencyTo {

    private final String code;

    public static CurrencyTo fromEntity(Currency currency) {
        return new CurrencyTo(currency.getCode());
    }
}
