package cz.profinit.training.springadvanced.springrest.chat;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatRating;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatRatingResponse;
import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ChatClientApplication {

    private static final int THREADS = 1000;
    private static final int MESSAGES = 10;
    private static final int MILLIS_BEFORE_STARTING_NEW_THREAD = 1;

    private static final Logger logger = LoggerFactory.getLogger(ChatClientApplication.class);

    public static void main(final String[] args) {
        new ChatClientApplication().sequences();
    }

    private void sequences() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            threads.add(new Thread(this::sequence));
        }

        threads.forEach(thread -> {
            sleepBeforeStartingNewThread();
            thread.start();
        });
    }

    private void sequence() {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = "http://localhost:8080/chat";

        logger.info("Status: {}", restTemplate.getForObject(url + "/status", ChatUpdate.class));

        final ChatUpdate start = restTemplate.postForObject(url + "/conversation", null, ChatUpdate.class);
        logger.info("Start: {}", start);

        logger.info("Sent: {}", restTemplate.postForObject(url + "/conversation/{sessionId}/message?text=Howdoyoudo", null, ChatUpdate.class, start.getSessionId()));

        for (int i = 0; i < MESSAGES; i++) {
            logger.info("Refresh: {}", restTemplate.getForObject(url + "/conversation/{sessionId}", ChatUpdate.class, start.getSessionId()));
        }

        logger.info("Finish");
        restTemplate.delete(url + "/conversation/{sessionId}", start.getSessionId());

        logger.info("Rating: {}", restTemplate.postForObject(url + "/conversation/{sessionId}/rating", new ChatRating(10, "scott", "Rather good"), ChatRatingResponse.class, start.getSessionId()));
    }

    private void sleepBeforeStartingNewThread() {
        try {
            Thread.sleep(MILLIS_BEFORE_STARTING_NEW_THREAD);
        } catch (InterruptedException e) {
            logger.warn("Interrupted", e);
        }
    }
}
