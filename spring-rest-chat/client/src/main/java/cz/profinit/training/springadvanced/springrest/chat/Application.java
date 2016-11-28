package cz.profinit.training.springadvanced.springrest.chat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(final String... args) throws Exception {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8080/chat";

        System.out.println("Status: " + restTemplate.getForObject(url + "/status", ChatUpdate.class));

        final ChatUpdate start = restTemplate.postForObject(url + "/conversation", null, ChatUpdate.class);
        System.out.println("Start: " + start);

        System.out.println("Send: " + restTemplate.postForObject(url + "/conversation/" + start.getSessionId() + "/message?text=Howdoyoudo", null, ChatUpdate.class));

        for (int i = 0; i < 3; i++) {
            System.out.println("Refresh: " + restTemplate.getForObject(url + "/conversation/" + start.getSessionId(), ChatUpdate.class));
        }

        System.out.println("Finish");
        restTemplate.delete(url + "/conversation/" + start.getSessionId());

        System.out.println("Rating: " + restTemplate.postForObject(url + "/conversation/" + start.getSessionId() + "/rating", new ChatRating(10, "scott", "Rather good"), ChatRatingResponse.class));
    }
}
