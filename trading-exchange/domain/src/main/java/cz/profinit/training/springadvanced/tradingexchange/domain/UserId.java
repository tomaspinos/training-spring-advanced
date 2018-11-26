package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserId {

    private final Long id;
}
