package cz.profinit.training.springadvanced.springrest.chat.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ChatRating implements Serializable {

    private int rating;
    private String username;
    private String note;

    public ChatRating() {
    }

    public ChatRating(int rating, String username, String note) {
        this.rating = rating;
        this.username = username;
        this.note = note;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
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
