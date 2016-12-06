package cz.profinit.training.springadvanced.springrest.chat.lifecycle;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessage;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType.INCOMING;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType.OUTGOING;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType.AVAILABLE;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType.FINISHED;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType.RUNNING;
import static java.util.Collections.singletonList;

@Component
public class ChatSecuredLifecycle {

    private static final String[] WELCOME_MESSAGES = {
            "Welcome!",
            "Here we go!",
            "What can I do you for?",
            "Identify <yourself>!",
            "State your purpose!"
    };

    private static final String[] INCOMING_MESSAGES = {
            "Ok",
            "Very well!",
            "Well done!",
            "Really?",
            "I'm sorry to hear that..."
    };

    private static final String[] GOODBYE_MESSAGES = {
            "Thanks!",
            "Enough is enough"
    };

    private static final Log logger = LogFactory.getLog(ChatSecuredLifecycle.class);

    private final Set<String> runningSessionIds = new HashSet<>();
    private final Set<String> deletedSessionIds = new HashSet<>();

    private final Random random = new Random();

    public ChatUpdate status() {
        logger.info("status");
        return new ChatUpdate(AVAILABLE);
    }

    public ChatUpdate start() {
        logger.info("start");
        return new ChatUpdate(RUNNING, createSession(), singletonList(new ChatMessage(INCOMING, randomWelcomeMessage())));
    }

    public ChatUpdate sendMessage(final String sessionId, final String text) {
        logger.info("send");
        verifySessionIsRunning(sessionId);
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(OUTGOING, text)), randomMessageId());
    }

    public ChatUpdate getMessage(final String sessionId, final String messageId) {
        logger.info("getMessage");
        verifySessionIsRunning(sessionId);
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(OUTGOING, "Censored")), messageId);
    }

    // TODO Require ROLE_ADMIN role
    public ChatUpdate modifyMessage(final String sessionId, final String messageId, final String text) {
        logger.info("modify");
        verifySessionIsRunning(sessionId);
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(OUTGOING, text)), messageId);
    }

    // TODO Require ROLE_ADMIN role
    public ChatUpdate deleteMessage(final String sessionId, final String messageId) {
        logger.info("delete");
        verifySessionIsRunning(sessionId);
        return new ChatUpdate(RUNNING, sessionId, Collections.emptyList(), messageId);
    }

    public ChatUpdate refresh(final String sessionId) {
        logger.info("refresh");
        verifySessionIsRunning(sessionId);
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(INCOMING, randomIncomingMessage())));
    }

    public ChatUpdate finish(final String sessionId) {
        logger.info("finish");
        verifySessionIsRunning(sessionId);
        deleteSession(sessionId);
        return new ChatUpdate(FINISHED, sessionId, singletonList(new ChatMessage(INCOMING, randomGoodbyeMessage())));
    }

    public ChatRatingResponse rating(final String sessionId, final ChatRating rating) {
        logger.info("rating");
        verifySessionIsRunningOrDeleted(sessionId);
        return new ChatRatingResponse(sessionId, rating, randomGoodbyeMessage());
    }

    private String createSession() {
        final String sessionId = randomSessionId();
        runningSessionIds.add(sessionId);
        return sessionId;
    }

    private void deleteSession(final String sessionId) {
        runningSessionIds.remove(sessionId);
        deletedSessionIds.add(sessionId);
    }

    private void verifySessionIsRunning(final String sessionId) {
        if (!runningSessionIds.contains(sessionId)) {
            throw new ChatSessionNotFoundException(sessionId);
        }
    }

    private void verifySessionIsRunningOrDeleted(final String sessionId) {
        if (!runningSessionIds.contains(sessionId) && !deletedSessionIds.contains(sessionId)) {
            throw new ChatSessionNotFoundException(sessionId);
        }
    }

    private String randomSessionId() {
        return RandomStringUtils.randomAlphanumeric(12).toUpperCase();
    }

    private String randomMessageId() {
        return RandomStringUtils.randomAlphanumeric(16).toUpperCase();
    }

    private String randomWelcomeMessage() {
        return WELCOME_MESSAGES[random.nextInt(WELCOME_MESSAGES.length)];
    }

    private String randomIncomingMessage() {
        return INCOMING_MESSAGES[random.nextInt(INCOMING_MESSAGES.length)];
    }

    private String randomGoodbyeMessage() {
        return GOODBYE_MESSAGES[random.nextInt(GOODBYE_MESSAGES.length)];
    }
}
