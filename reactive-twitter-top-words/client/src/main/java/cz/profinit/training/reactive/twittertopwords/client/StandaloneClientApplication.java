package cz.profinit.training.reactive.twittertopwords.client;

import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class StandaloneClientApplication {

    public static void main(String[] args) {
        Flux<TopWords> topWordsFlux = WebClient.create("http://localhost:8080/twitter-top-words/top-words")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TopWords.class);

        AtomicInteger topWordsCounter = new AtomicInteger(1);

        Disposable topWordsSubscription = topWordsFlux.subscribe(topWords ->
                System.out.println(topWordsCounter.getAndIncrement() + ": " + topWords));

        System.out.println("Starting streaming");

        while (topWordsCounter.get() < 1000) {
        }

        topWordsSubscription.dispose();

        System.out.println("Streaming finished");
    }
}
