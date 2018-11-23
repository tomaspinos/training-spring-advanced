package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class TradeId {

    private final Long id;
}
