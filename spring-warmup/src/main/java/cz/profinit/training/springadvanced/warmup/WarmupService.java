package cz.profinit.training.springadvanced.warmup;

import cz.profinit.training.springadvanced.warmup.support.Clock;

public class WarmupService {

    private final Clock localDateTimeProvider;

    public WarmupService(final Clock localDateTimeProvider) {
        this.localDateTimeProvider = localDateTimeProvider;
    }

    public String sayHello(final String text) {
        return "Hello " + text + " at " + localDateTimeProvider.get();
    }
}
