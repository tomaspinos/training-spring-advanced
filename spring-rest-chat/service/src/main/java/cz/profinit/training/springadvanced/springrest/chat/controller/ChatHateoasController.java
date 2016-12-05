package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatLifecycle;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/chat-hateoas")
public class ChatHateoasController {

    private final ChatLifecycle lifecycle;

    @Autowired
    public ChatHateoasController(final ChatLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate status() {
        return lifecycle.status();
    }

    @PostMapping("/conversation")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ChatUpdateResource> start() {
        final ChatUpdate update = lifecycle.start();
        final ChatUpdateResource resource = new ChatUpdateResource(update);
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ChatHateoasController.class).refresh(update.getSessionId())).withSelfRel());
        return ResponseEntity.created(URI.create(resource.getLink("self").getHref()))
                .body(resource);
    }

    @PostMapping("/conversation/{sessionId}/message")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ChatUpdateResource> send(@PathVariable final String sessionId, @RequestParam final String text) {
        final ChatUpdate update = lifecycle.sendMessage(sessionId, text);
        final ChatUpdateResource resource = new ChatUpdateResource(update);
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ChatHateoasController.class).message(update.getSessionId(), update.getMessageId())).withSelfRel());
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ChatHateoasController.class).refresh(update.getSessionId())).withRel("conversation"));
        return ResponseEntity.created(URI.create(resource.getLink("self").getHref()))
                .body(resource);
    }

    @GetMapping("/conversation/{sessionId}/message/{messageId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate message(@PathVariable final String sessionId, @PathVariable final String messageId) {
        return lifecycle.getMessage(sessionId, messageId);
    }

    @PutMapping("/conversation/{sessionId}/message/{messageId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate modify(@PathVariable final String sessionId, @PathVariable final String messageId, @RequestParam final String text) {
        return lifecycle.modifyMessage(sessionId, messageId, text);
    }

    @DeleteMapping("/conversation/{sessionId}/message/{messageId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate delete(@PathVariable final String sessionId, @PathVariable final String messageId) {
        return lifecycle.deleteMessage(sessionId, messageId);
    }

    @GetMapping("/conversation/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ChatUpdateResource> refresh(@PathVariable final String sessionId) {
        final ChatUpdate update = lifecycle.refresh(sessionId);
        final ChatUpdateResource resource = new ChatUpdateResource(update);
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ChatHateoasController.class).refresh(sessionId)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/conversation/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate finish(@PathVariable final String sessionId) {
        return lifecycle.finish(sessionId);
    }

    @PostMapping("/conversation/{sessionId}/rating")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatRatingResponse rating(@PathVariable final String sessionId, @RequestBody final ChatRating rating) {
        return lifecycle.rating(sessionId, rating);
    }
}
