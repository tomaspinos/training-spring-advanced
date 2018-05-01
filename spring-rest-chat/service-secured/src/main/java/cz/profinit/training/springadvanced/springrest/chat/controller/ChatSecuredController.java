package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatSecuredLifecycle;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/chat")
public class ChatSecuredController {

    private final ChatSecuredLifecycle lifecycle;

    public ChatSecuredController(final ChatSecuredLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate status() {
        return lifecycle.status();
    }

    @PostMapping("/conversation")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatUpdate start() {
        return lifecycle.start();
    }

    @PostMapping("/conversation/{sessionId}/message")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatUpdate send(@PathVariable final String sessionId, @RequestParam final String text) {
        return lifecycle.sendMessage(sessionId, text);
    }

    @PutMapping("/conversation/{sessionId}/message/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate modify(@PathVariable final String sessionId, @PathVariable final String messageId, @RequestParam final String text) {
        return lifecycle.modifyMessage(sessionId, messageId, text);
    }

    @DeleteMapping("/conversation/{sessionId}/message/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate delete(@PathVariable final String sessionId, @PathVariable final String messageId) {
        return lifecycle.deleteMessage(sessionId, messageId);
    }

    @GetMapping("/conversation/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate refresh(@PathVariable final String sessionId) {
        return lifecycle.refresh(sessionId);
    }

    @DeleteMapping("/conversation/{sessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate finish(@PathVariable final String sessionId) {
        return lifecycle.finish(sessionId);
    }

    @PostMapping("/conversation/{sessionId}/rating")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRatingResponse rating(@PathVariable final String sessionId, @RequestBody final ChatRating rating) {
        return lifecycle.rating(sessionId, rating);
    }
}
