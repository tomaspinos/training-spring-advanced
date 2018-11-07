package cz.profinit.training.springadvanced.integration.support;

import java.time.LocalDateTime;

public class DefaultClock implements Clock {

    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
