package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderId {

    private final Long id;
}
