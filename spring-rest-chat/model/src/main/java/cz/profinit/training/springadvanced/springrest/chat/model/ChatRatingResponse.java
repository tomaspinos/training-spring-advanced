package cz.profinit.training.springadvanced.springrest.chat.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ChatRatingResponse implements Serializable {

    private String sessionId;
    private ChatRating rating;
    private String goodbyeMessage;

    public ChatRatingResponse() {
    }

    public ChatRatingResponse(String sessionId, ChatRating rating, String goodbyeMessage) {
        this.sessionId = sessionId;
        this.rating = rating;
        this.goodbyeMessage = goodbyeMessage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ChatRating getRating() {
        return rating;
    }

    public void setRating(ChatRating rating) {
        this.rating = rating;
    }

    public String getGoodbyeMessage() {
        return goodbyeMessage;
    }

    public void setGoodbyeMessage(String goodbyeMessage) {
        this.goodbyeMessage = goodbyeMessage;
    }

    @Override
    public String toString() {
        return "ChatRatingResponse{" +
                "rating=" + rating +
                ", goodbyeMessage='" + goodbyeMessage + '\'' +
                '}';
    }
}
