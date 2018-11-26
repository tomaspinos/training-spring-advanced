package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SampleData {

    private static final AtomicLong id = new AtomicLong(1);

    public static final Currency EUR = Currency.builder().id(id.getAndIncrement()).code("EUR").build();
    public static final Currency CZK = Currency.builder().id(id.getAndIncrement()).code("CZK").build();
    public static final User USER_A = User.builder().id(id.getAndIncrement()).username(Username.of("a")).build();
    public static final User USER_B = User.builder().id(id.getAndIncrement()).username(Username.of("b")).build();
    public static final User USER_C = User.builder().id(id.getAndIncrement()).username(Username.of("c")).build();
}
