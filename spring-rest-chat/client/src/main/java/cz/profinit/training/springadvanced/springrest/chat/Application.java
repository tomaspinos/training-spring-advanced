package cz.profinit.training.springadvanced.springrest.chat;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(String args[]) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/chat";

        System.out.println("Status: " + restTemplate.getForObject(url + "/status", ChatUpdate.class));

        ChatUpdate start = restTemplate.getForObject(url + "/start", ChatUpdate.class);
        System.out.println("Start: " + start);

        System.out.println("Send: " + restTemplate.postForObject(url + "/send/" + start.getSessionId() + "?text=Howdoyoudo", null, ChatUpdate.class));

        for (int i = 0; i < 3; i++) {
            System.out.println("Refresh: " + restTemplate.getForObject(url + "/refresh/" + start.getSessionId(), ChatUpdate.class));
        }

        System.out.println("Finish: " + restTemplate.getForObject(url + "/finish/" + start.getSessionId(), ChatUpdate.class));

        System.out.println("Rating: " + restTemplate.postForObject(url + "/rating/" + start.getSessionId(), new ChatRating(10, "scott", "Rather good"), ChatRatingResponse.class));
    }
}
