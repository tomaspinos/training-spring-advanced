package cz.profinit.training.springadvanced.servletapi;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCounterBean implements Serializable {

    private final AtomicInteger counter = new AtomicInteger(0);

    public int inc() {
        return counter.incrementAndGet();
    }
}
