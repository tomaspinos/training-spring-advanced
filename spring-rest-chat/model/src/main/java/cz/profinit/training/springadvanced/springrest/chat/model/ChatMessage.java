package cz.profinit.training.springadvanced.springrest.chat.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChatMessage {

    private ChatMessageDirectionType direction;
    private String text;

    public ChatMessage() {
    }

    public ChatMessage(final ChatMessageDirectionType direction, final String text) {
        this.direction = direction;
        this.text = text;
    }

    public ChatMessageDirectionType getDirection() {
        return direction;
    }

    public void setDirection(final ChatMessageDirectionType direction) {
        this.direction = direction;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "direction=" + direction +
                ", text='" + text + '\'' +
                '}';
    }
}
