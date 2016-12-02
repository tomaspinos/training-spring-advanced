package cz.profinit.training.springadvanced.warmup.support;

import java.time.LocalDateTime;

public class DefaultLocalDateTimeProvider implements LocalDateTimeProvider {

    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
