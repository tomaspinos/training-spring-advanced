package cz.profinit.training.springadvanced.warmup;

import cz.profinit.training.springadvanced.warmup.support.LocalDateTimeProvider;

public class WarmupService {

    private final LocalDateTimeProvider localDateTimeProvider;

    public WarmupService(final LocalDateTimeProvider localDateTimeProvider) {
        this.localDateTimeProvider = localDateTimeProvider;
    }

    public String sayHello(final String text) {
        return "Hello " + text + " at " + localDateTimeProvider.get();
    }
}
