package cz.profinit.training.springadvanced.springrest.chat.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
@XmlRootElement
public class ChatUpdate implements Serializable {

    private ChatStatusType status;
    private String sessionId;
    private List<ChatMessage> messages;

    public ChatUpdate() {
    }

    public ChatUpdate(ChatStatusType status) {
        this(status, null, Collections.emptyList());
    }

    public ChatUpdate(ChatStatusType status, String sessionId, List<ChatMessage> messages) {
        this.status = status;
        this.sessionId = sessionId;
        this.messages = messages;
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

    @Override
    public String toString() {
        return "ChatUpdate{" +
                "status=" + status +
                ", sessionId='" + sessionId + '\'' +
                ", messages=" + messages +
                '}';
    }
}
