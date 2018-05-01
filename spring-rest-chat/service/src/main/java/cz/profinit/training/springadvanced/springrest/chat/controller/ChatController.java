package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatLifecycle;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;

/**
 * TODO Annotate class and methods
 */
public class ChatController {

    private final ChatLifecycle lifecycle;

    public ChatController(final ChatLifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    public ChatUpdate status() {
        return lifecycle.status();
    }

    public ChatUpdate start() {
        return lifecycle.start();
    }

    public ChatUpdate send(final String sessionId, final String text) {
        return lifecycle.sendMessage(sessionId, text);
    }

    public ChatUpdate modify(final String sessionId, final String messageId, final String text) {
        return lifecycle.modifyMessage(sessionId, messageId, text);
    }

    public ChatUpdate delete(final String sessionId, final String messageId) {
        return lifecycle.deleteMessage(sessionId, messageId);
    }

    public ChatUpdate refresh(final String sessionId) {
        return lifecycle.refresh(sessionId);
    }

    public ChatUpdate finish(final String sessionId) {
        return lifecycle.finish(sessionId);
    }

    public ChatRatingResponse rating(final String sessionId, final ChatRating rating) {
        return lifecycle.rating(sessionId, rating);
    }
}
