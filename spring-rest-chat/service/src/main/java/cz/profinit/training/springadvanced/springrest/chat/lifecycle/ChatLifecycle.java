package cz.profinit.training.springadvanced.springrest.chat.lifecycle;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatMessage;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

import static cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType.INCOMING;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatMessageDirectionType.OUTGOING;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType.AVAILABLE;
import static cz.profinit.training.springadvanced.springrest.chat.model.ChatStatusType.RUNNING;
import static java.util.Collections.singletonList;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
@Component
public class ChatLifecycle {

    static final String[] WELCOME_MESSAGES = {
            "Welcome!",
            "Here we go!",
            "What can I do you for?",
            "Identify <yourself>!"
    };

    static final String[] INCOMING_MESSAGES = {
            "Ok",
            "Very well!",
            "Well done!",
            "Really?",
            "I'm sorry to hear that..."
    };

    static final String[] GOODBYE_MESSAGES = {
            "Thanks!",
            "Enough is enough"
    };

    private Random random = new Random();

    public ChatUpdate status() {
        return new ChatUpdate(AVAILABLE);
    }

    public ChatUpdate start() {
        return new ChatUpdate(RUNNING, randomSessionId(), singletonList(new ChatMessage(INCOMING, randomWelcomeMessage())));
    }

    public ChatUpdate send(String sessionId, String text) {
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(OUTGOING, text)));
    }

    public ChatUpdate refresh(String sessionId) {
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(INCOMING, randomIncomingMessage())));
    }

    public ChatUpdate finish(String sessionId) {
        return new ChatUpdate(RUNNING, sessionId, singletonList(new ChatMessage(INCOMING, randomGoodbyeMessage())));
    }

    public ChatRatingResponse rating(String sessionId, ChatRating rating) {
        return new ChatRatingResponse(sessionId, rating, randomGoodbyeMessage());
    }

    protected String randomSessionId() {
        return RandomStringUtils.randomAlphanumeric(12).toUpperCase();
    }

    protected String randomWelcomeMessage() {
        return WELCOME_MESSAGES[random.nextInt(WELCOME_MESSAGES.length)];
    }

    protected String randomIncomingMessage() {
        return INCOMING_MESSAGES[random.nextInt(INCOMING_MESSAGES.length)];
    }

    protected String randomGoodbyeMessage() {
        return GOODBYE_MESSAGES[random.nextInt(GOODBYE_MESSAGES.length)];
    }
}
