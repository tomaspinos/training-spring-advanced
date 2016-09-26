package cz.profinit.training.springadvanced.springrest.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatLifecycle lifecycle;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate status() {
        return lifecycle.status();
    }

    @RequestMapping(value = "/conversation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ChatUpdate start() {
        return lifecycle.start();
    }

    @RequestMapping(value = "/conversation/{sessionId}/message", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate send(@PathVariable String sessionId, @RequestParam String text) {
        return lifecycle.send(sessionId, text);
    }

    @RequestMapping(value = "/conversation/{sessionId}/message/{messageId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate modify(@PathVariable String sessionId, @PathVariable String messageId, @RequestParam String text) {
        return lifecycle.modify(sessionId, messageId, text);
    }

    @RequestMapping(value = "/conversation/{sessionId}/message/{messageId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatUpdate modify(@PathVariable String sessionId, @PathVariable String messageId) {
        return lifecycle.delete(sessionId, messageId);
    }

    @RequestMapping(value = "/conversation/{sessionId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate refresh(@PathVariable String sessionId) {
        return lifecycle.refresh(sessionId);
    }

    @RequestMapping(value = "/conversation/{sessionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ChatUpdate finish(@PathVariable String sessionId) {
        return lifecycle.finish(sessionId);
    }

    @RequestMapping(value = "/conversation/{sessionId}/rating", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ChatRatingResponse rating(@PathVariable String sessionId, @RequestBody ChatRating rating) {
        return lifecycle.rating(sessionId, rating);
    }
}
