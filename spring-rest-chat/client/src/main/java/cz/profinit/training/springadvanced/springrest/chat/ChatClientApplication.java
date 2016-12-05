package cz.profinit.training.springadvanced.springrest.chat;

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

        // TODO start
        // TODO send message
        // TODO refresh
        // TODO delete
        // TODO rating
    }
}
