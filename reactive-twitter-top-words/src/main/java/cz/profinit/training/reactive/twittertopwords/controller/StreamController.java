package cz.profinit.training.reactive.twittertopwords.controller;

import cz.profinit.training.reactive.twittertopwords.model.TopWords;
import cz.profinit.training.reactive.twittertopwords.streaming.TopWordsFluxFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

// TODO Implement methods for top words streaming and top words list (during 3 seconds)
@RestController
public class StreamController {

    private final Flux<TopWords> flux;

    private static final Logger log = LoggerFactory.getLogger(StreamController.class);

    public StreamController(TopWordsFluxFactory topWordsFluxFactory) {
        this.flux = topWordsFluxFactory.createTopWordsFlux();
    }
}
