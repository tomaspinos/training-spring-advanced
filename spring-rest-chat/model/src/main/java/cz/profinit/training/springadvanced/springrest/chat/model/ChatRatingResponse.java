package cz.profinit.training.springadvanced.springrest.chat.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChatRatingResponse implements Serializable {

    private String sessionId;
    private ChatRating rating;
    private String goodbyeMessage;

    public ChatRatingResponse() {
    }

    public ChatRatingResponse(final String sessionId, final ChatRating rating, final String goodbyeMessage) {
        this.sessionId = sessionId;
        this.rating = rating;
        this.goodbyeMessage = goodbyeMessage;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    public ChatRating getRating() {
        return rating;
    }

    public void setRating(final ChatRating rating) {
        this.rating = rating;
    }

    public String getGoodbyeMessage() {
        return goodbyeMessage;
    }

    public void setGoodbyeMessage(final String goodbyeMessage) {
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
