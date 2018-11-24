package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SampleData {

    public static final Currency EUR = Currency.builder().id(1L).code("EUR").build();
    public static final Currency CZK = Currency.builder().id(2L).code("CZK").build();
}
