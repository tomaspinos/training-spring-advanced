package cz.profinit.training.reactive.twittertopwords.client;

import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class StandaloneClientApplication {

    public static void main(String[] args) throws InterruptedException {
        // TODO Use WebClient to stream top words from web
        Flux<TopWords> flux = Flux.empty();

        Disposable subscription = flux.subscribe(System.out::println);

        System.out.println("Starting streaming");

        Thread.sleep(10000);

        subscription.dispose();

        System.out.println("Streaming finished");
    }
}
