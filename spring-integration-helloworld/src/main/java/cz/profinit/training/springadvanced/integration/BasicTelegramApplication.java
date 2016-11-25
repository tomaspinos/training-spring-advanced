package cz.profinit.training.springadvanced.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;

import java.util.stream.Collectors;

/**
 * Flow accepts a sentence ({@link String}, splits it to words, capitalizes them and joins them with " - ".
 * Just like telegrams do.
 * The goal is to demonstrate message splitting, transformation and aggregation.
 */
@SpringBootApplication
@IntegrationComponentScan
public class BasicTelegramApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BasicTelegramApplication.class, args);

        TelegramGateway gateway = ctx.getBean(TelegramGateway.class);

        gateway.send("Calling Occupants of Interplanetary Craft");

        ctx.close();
    }

    @Bean
    public IntegrationFlow telegramFlow() {
        return f -> f
                .split(String.class, s -> s.split(" "))
                .<String, String>transform(String::toUpperCase)
                // Consumer<AggregatorSpec> aggregator
                .aggregate(aggregator -> aggregator
                        .outputProcessor(group -> group
                                .getMessages().stream()
                                .map(message -> (String) message.getPayload()).collect(Collectors.joining(" - "))))
                .handle(CharacterStreamWritingMessageHandler.stdout());
    }

    @MessagingGateway
    interface TelegramGateway {

        @Gateway(requestChannel = "telegramFlow.input")
        void send(String message);
    }
}
