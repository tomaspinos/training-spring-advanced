package cz.profinit.training.springadvanced.integration.support;

import java.time.LocalDateTime;

public class DefaultLocalDateTimeProvider implements LocalDateTimeProvider {

    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
