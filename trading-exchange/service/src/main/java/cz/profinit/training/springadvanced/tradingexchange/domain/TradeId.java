package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class TradeId implements Serializable {

    private final Long id;
}
