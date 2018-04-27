package cz.profinit.training.reactive.twittertopwords.controller;

import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import cz.profinit.training.reactive.twittertopwords.streaming.TopWordsFluxFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/twitter-top-words")
@CrossOrigin
public class StreamController {

    private final TopWordsFluxFactory topWordsFluxFactory;

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    public StreamController(TopWordsFluxFactory topWordsFluxFactory) {
        this.topWordsFluxFactory = topWordsFluxFactory;
    }

    @GetMapping("/top-words")
    public Flux<TopWords> streamTopWords() {
        log.info("Streaming top words");
        return topWordsFluxFactory.createTopWordsFlux();
    }

    @ExceptionHandler(IOException.class)
    public void handleException(IOException e) {
        log.warn("IOException occurred: {}", e.getMessage());
    }
}
