package cz.profinit.training.springadvanced.springrest.chat.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatSessionNotFoundException;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessage;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;

@ControllerAdvice
public class ChatControllerAdvice {

    @ExceptionHandler(ChatSessionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ChatUpdate chatSessionNotFound(final ChatSessionNotFoundException e) {
        System.err.println("Chat session not found: " + e.getSessionId());
        return new ChatUpdate(ChatStatusType.ERROR, e.getSessionId(),
                Collections.singletonList(new ChatMessage(ChatMessageDirectionType.INCOMING, "Chat session not found")));
    }
}
