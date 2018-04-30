package cz.profinit.training.springadvanced.warmup;

import cz.profinit.training.springadvanced.warmup.support.Clock;

public class WarmupService {

    private final Clock clock;

    public WarmupService(final Clock clock) {
        this.clock = clock;
    }

    public String sayHello(final String text) {
        return "Hello " + text + " at " + clock.get();
    }
}
