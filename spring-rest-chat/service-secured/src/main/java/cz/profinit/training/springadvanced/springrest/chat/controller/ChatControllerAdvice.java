package cz.profinit.training.springadvanced.springrest.chat.controller;

import cz.profinit.training.springadvanced.springrest.chat.lifecycle.ChatSessionNotFoundException;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessage;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
public class ChatControllerAdvice {

    private static final Log logger = LogFactory.getLog(ChatControllerAdvice.class);

    @ExceptionHandler(ChatSessionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ChatUpdate chatSessionNotFound(final ChatSessionNotFoundException e) {
        logger.error("Chat session not found: " + e.getSessionId());
        return new ChatUpdate(ChatStatusType.ERROR, e.getSessionId(),
                Collections.singletonList(new ChatMessage(ChatMessageDirectionType.INCOMING, "Chat session not found")));
    }
}
