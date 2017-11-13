package cz.profinit.training.springadvanced.springrest.chat;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ChatClientApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(ChatClientApplication.class);
    }

    @Override
    public void run(final String... args) throws Exception {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8080/chat";

        System.out.println("Status: " + restTemplate.getForObject(url + "/status", ChatUpdate.class));

        final ChatUpdate start = restTemplate.postForObject(url + "/conversation", null, ChatUpdate.class);
        System.out.println("Start: " + start);

        System.out.println("Send: " + restTemplate.postForObject(url + "/conversation/{sessionId}/message?text=Howdoyoudo", null, ChatUpdate.class, start.getSessionId()));

        for (int i = 0; i < 3; i++) {
            System.out.println("Refresh: " + restTemplate.getForObject(url + "/conversation/{sessionId}", ChatUpdate.class, start.getSessionId()));
        }

        System.out.println("Finish");
        restTemplate.delete(url + "/conversation/{sessionId}", start.getSessionId());

        System.out.println("Rating: " + restTemplate.postForObject(url + "/conversation/{sessionId}/rating", new ChatRating(10, "scott", "Rather good"), ChatRatingResponse.class, start.getSessionId()));
    }
}
