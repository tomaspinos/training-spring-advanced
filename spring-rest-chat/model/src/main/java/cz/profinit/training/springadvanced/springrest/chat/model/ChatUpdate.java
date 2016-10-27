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

    public ChatUpdate(ChatStatusType status) {
        this(status, null, Collections.emptyList());
    }

    public ChatUpdate(ChatStatusType status, String sessionId, List<ChatMessage> messages) {
        this(status, sessionId, messages, null);
    }

    public ChatUpdate(ChatStatusType status, String sessionId, List<ChatMessage> messages, String messageId) {
        this.status = status;
        this.sessionId = sessionId;
        this.messages = messages;
        this.messageId = messageId;
    }

    public ChatStatusType getStatus() {
        return status;
    }

    public void setStatus(ChatStatusType status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
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
