package cz.profinit.training.springadvanced.springrest.chat.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatLifecycle;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;

@RestController
@RequestMapping("/chat-hateoas")
public class ChatHateoasController {

    private final ChatLifecycle lifecycle;

    @Autowired
    public ChatHateoasController(final ChatLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate status() {
        return lifecycle.status();
    }

    @RequestMapping(value = "/conversation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ChatUpdateResource> start() {
        final ChatUpdate update = lifecycle.start();
        final ChatUpdateResource resource = new ChatUpdateResource(update);
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ChatHateoasController.class).refresh(update.getSessionId())).withSelfRel());
        return ResponseEntity.created(URI.create(resource.getLink("self").getHref()))
                .body(resource);
    }

    @RequestMapping(value = "/conversation/{sessionId}/message", method = RequestMethod.POST)
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

    @RequestMapping(value = "/conversation/{sessionId}/message/{messageId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate message(@PathVariable final String sessionId, @PathVariable final String messageId) {
        return lifecycle.getMessage(sessionId, messageId);
    }

    @RequestMapping(value = "/conversation/{sessionId}/message/{messageId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate modify(@PathVariable final String sessionId, @PathVariable final String messageId, @RequestParam final String text) {
        return lifecycle.modifyMessage(sessionId, messageId, text);
    }

    @RequestMapping(value = "/conversation/{sessionId}/message/{messageId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate delete(@PathVariable final String sessionId, @PathVariable final String messageId) {
        return lifecycle.deleteMessage(sessionId, messageId);
    }

    @RequestMapping(value = "/conversation/{sessionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ChatUpdateResource> refresh(@PathVariable final String sessionId) {
        final ChatUpdate update = lifecycle.refresh(sessionId);
        final ChatUpdateResource resource = new ChatUpdateResource(update);
        resource.add(ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(ChatHateoasController.class).refresh(sessionId)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @RequestMapping(value = "/conversation/{sessionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate finish(@PathVariable final String sessionId) {
        return lifecycle.finish(sessionId);
    }

    @RequestMapping(value = "/conversation/{sessionId}/rating", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatRatingResponse rating(@PathVariable final String sessionId, @RequestBody final ChatRating rating) {
        return lifecycle.rating(sessionId, rating);
    }
}
