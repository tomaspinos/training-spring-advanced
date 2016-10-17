package cz.profinit.training.springadvanced.integration;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;

/**
 * Flow accepts {@link String} message and prepends time and "Hello " to it.
 */
@SpringBootApplication
@IntegrationComponentScan
public class HelloWorldApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(HelloWorldApplication.class, args);

        StringGateway gateway = ctx.getBean(StringGateway.class);
        gateway.place("World");

        ctx.close();
    }

    @Bean
    public IntegrationFlow helloWorldFlow() {
        return f -> f
                .transform(String.class, s -> LocalDateTime.now() + " Hello " + s + "\n")
                .<String, String>transform(String::toUpperCase)
                .handle(CharacterStreamWritingMessageHandler.stdout());
    }

    @MessagingGateway
    public interface StringGateway {

        @Gateway(requestChannel = "helloWorldFlow.input")
        void place(String input);
    }
}
