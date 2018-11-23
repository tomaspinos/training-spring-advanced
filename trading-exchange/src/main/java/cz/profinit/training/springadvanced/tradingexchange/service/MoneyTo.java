package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
public class MoneyTo implements Serializable {

    private final CurrencyTo currency;
    private final BigDecimal amount;

    public static MoneyTo fromEntity(Money money) {
        return new MoneyTo(CurrencyTo.fromEntity(money.getCurrency()), money.getAmount());
    }
}
