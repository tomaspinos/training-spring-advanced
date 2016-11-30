package cz.profinit.training.springadvanced.springrest.chat.controller;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;

public class ChatUpdateResource extends ResourceSupport {

    private final ChatUpdate update;

    public ChatUpdateResource(final ChatUpdate update) {
        this.update = update;
    }

    @JsonUnwrapped
    public ChatUpdate getUpdate() {
        return update;
    }
}
