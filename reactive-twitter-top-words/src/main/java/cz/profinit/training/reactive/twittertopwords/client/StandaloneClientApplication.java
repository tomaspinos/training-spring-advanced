package cz.profinit.training.reactive.twittertopwords.client;

import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

public class StandaloneClientApplication {

    public static void main(String[] args) throws InterruptedException {
        Flux<TopWords> topWordsFlux = WebClient.create("http://localhost:8080/twitter-top-words/top-words")
                .method(HttpMethod.GET)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(TopWords.class);

        Disposable topWordsSubscription = topWordsFlux.subscribe(System.out::println);

        System.out.println("Starting streaming");

        Thread.sleep(10000);

        topWordsSubscription.dispose();

        System.out.println("Streaming finished");
    }
}
