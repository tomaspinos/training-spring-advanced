package cz.profinit.training.springadvanced.servletapi;

public class RequestCounterBean {

    int counter = 0;

    public synchronized int inc() {
        return counter++;
    }

}
