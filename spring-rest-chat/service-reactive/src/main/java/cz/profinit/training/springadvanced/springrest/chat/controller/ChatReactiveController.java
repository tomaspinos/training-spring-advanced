package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatLifecycle;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "/chat", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE})
public class ChatReactiveController {

    private final ChatLifecycle lifecycle;

    private static final Logger logger = LoggerFactory.getLogger(ChatReactiveController.class);

    public ChatReactiveController(final ChatLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ChatUpdate> status() {
        return Mono.just(lifecycle.status());
    }

    @PostMapping("/conversation")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ChatUpdate> start() {
        return Mono.just(lifecycle.start());
    }

    @PostMapping("/conversation/{sessionId}/message")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ChatUpdate> send(@PathVariable final String sessionId, @RequestParam final String text) {
        return Mono.just(lifecycle.sendMessage(sessionId, text));
    }

    @GetMapping("/conversation/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ChatUpdate> refresh(@PathVariable final String sessionId) {
        logger.info("refresh({})", sessionId);
        return Mono.just(lifecycle.refresh(sessionId));
    }

    @GetMapping(value = "/conversation/stream/{sessionId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<ChatUpdate> updateStream(@PathVariable final String sessionId) {
        logger.info("updateStream({})", sessionId);
        return Flux.interval(Duration.of(2, ChronoUnit.SECONDS))
                .map(l -> lifecycle.refresh(sessionId));
    }

    @DeleteMapping("/conversation/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ChatUpdate> finish(@PathVariable final String sessionId) {
        return Mono.just(lifecycle.finish(sessionId));
    }

    @PostMapping("/conversation/{sessionId}/rating")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ChatRatingResponse> rating(@PathVariable final String sessionId, @RequestBody final ChatRating rating) {
        return Mono.just(lifecycle.rating(sessionId, rating));
    }
}
