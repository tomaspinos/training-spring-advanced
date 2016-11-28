package cz.profinit.training.springadvanced.springrest.chat.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChatUpdate implements Serializable {

    private ChatStatusType status;
    private String sessionId;
    private List<ChatMessage> messages;
    private String messageId;

    public ChatUpdate() {
    }

    public ChatUpdate(final ChatStatusType status) {
        this(status, null, Collections.emptyList());
    }

    public ChatUpdate(final ChatStatusType status, final String sessionId, final List<ChatMessage> messages) {
        this(status, sessionId, messages, null);
    }

    public ChatUpdate(final ChatStatusType status, final String sessionId, final List<ChatMessage> messages, final String messageId) {
        this.status = status;
        this.sessionId = sessionId;
        this.messages = messages;
        this.messageId = messageId;
    }

    public ChatStatusType getStatus() {
        return status;
    }

    public void setStatus(final ChatStatusType status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(final List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return "ChatUpdate{" +
               "status=" + status +
               ", sessionId='" + sessionId + '\'' +
               ", messages=" + messages +
               ", messageId='" + messageId + '\'' +
               '}';
    }
}
