package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class CurrencyPairId {

    private final Long id;
}
