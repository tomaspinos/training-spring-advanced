package cz.profinit.training.springadvanced.springrest.chat;

import cz.profinit.training.springadvanced.springrest.chat.model.ChatUpdate;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ChatReactiveClientApplication {

    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080/chat");

        ChatUpdate status = webClient.get()
                .uri("/status")
                .exchange()
                .flatMap(response -> response.bodyToMono(ChatUpdate.class))
                .block();

        System.out.println("status = " + status);

        ChatUpdate start = webClient.post()
                .uri("/conversation")
                .exchange()
                .flatMap(response -> response.bodyToMono(ChatUpdate.class))
                .block();

        System.out.println("start = " + start);

        ChatUpdate firstMessage = webClient.post()
                .uri("/conversation/{sessionId}/message?text=Howdoyoudo", start.getSessionId())
                .exchange()
                .flatMap(response -> response.bodyToMono(ChatUpdate.class))
                .block();

        System.out.println("firstMessage = " + firstMessage);

        for (int i = 0; i < 3; i++) {
            ChatUpdate update = webClient.get()
                    .uri("/conversation/{sessionId}", start.getSessionId())
                    .exchange()
                    .flatMapMany(response -> response.bodyToMono(ChatUpdate.class))
                    .blockLast();

            System.out.println("Refresh " + i + ": " + update);
        }

        // curl http://localhost:8080/chat/conversation/stream/VSKVDJVQT0GS -i -H "Accept: application/stream+json"
        webClient.get()
                .uri("/conversation/stream/{sessionId}", start.getSessionId())
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .subscribe(response -> System.out.println("Refresh from stream: " + response.bodyToMono(ChatUpdate.class).block()));

        Thread.sleep(10000);
//        System.out.println("Finish");
//        webClient.delete().uri("/conversation/{sessionId}", start.getSessionId()).exchange().block();
//
//        ChatRatingResponse rating = webClient.post()
//                .uri("/conversation/{sessionId}/rating", start.getSessionId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(new ChatRating(10, "scott", "Rather good")))
//                .exchange()
//                .flatMap(response -> response.bodyToMono(ChatRatingResponse.class))
//                .block();
//
//        System.out.println("Rating: " + rating);
    }
}
