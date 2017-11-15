package cz.profinit.training.springadvanced.springrest.chat.lifecycle;

public class ChatSessionNotFoundException extends RuntimeException {

    private final String sessionId;

    public ChatSessionNotFoundException(final String sessionId) {
        super("Chat session not found: " + sessionId);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
