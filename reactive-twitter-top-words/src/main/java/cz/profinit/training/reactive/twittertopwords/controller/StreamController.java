package cz.profinit.training.reactive.twittertopwords.controller;

import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import cz.profinit.training.reactive.twittertopwords.streaming.TopWordsFluxFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/twitter-top-words")
@CrossOrigin
public class StreamController {

    private final Flux<TopWords> flux;

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    public StreamController(TopWordsFluxFactory topWordsFluxFactory) {
        this.flux = topWordsFluxFactory.createTopWordsFlux();
    }

    @GetMapping(path = "/top-words", produces = {MediaType.TEXT_EVENT_STREAM_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
    public Flux<TopWords> streamTopWords() {
        log.info("Streaming top words");
        return flux;
    }

    @GetMapping(path = "/top-words", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TopWords> getTopWords() {
        log.info("Getting some top words (waiting 3 seconds)");
        return flux.take(Duration.ofSeconds(3));
    }

    @ExceptionHandler(IOException.class)
    public void handleException(IOException e) {
        log.warn("IOException occurred: {}", e.getMessage());
    }
}
