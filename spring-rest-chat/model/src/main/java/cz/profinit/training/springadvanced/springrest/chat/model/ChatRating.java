package cz.profinit.training.springadvanced.springrest.chat.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChatRating implements Serializable {

    private int rating;
    private String username;
    private String note;

    public ChatRating() {
    }

    public ChatRating(final int rating, final String username, final String note) {
        this.rating = rating;
        this.username = username;
        this.note = note;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(final int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ChatRating{" +
                "rating=" + rating +
                ", username='" + username + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
