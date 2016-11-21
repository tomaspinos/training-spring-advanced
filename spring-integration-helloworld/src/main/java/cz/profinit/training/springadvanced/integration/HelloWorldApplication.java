package cz.profinit.training.springadvanced.integration;

import java.io.OutputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.stream.ByteStreamWritingMessageHandler;

import cz.profinit.training.springadvanced.integration.support.DefaultLocalDateTimeProvider;
import cz.profinit.training.springadvanced.integration.support.LocalDateTimeProvider;

/**
 * Flow accepts {@link String} message and prepends time and "Hello " to it.
 */
@SpringBootApplication
@IntegrationComponentScan
public class HelloWorldApplication {

    public static void main(final String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(HelloWorldApplication.class, args);

        final HelloWorldGateway gateway = ctx.getBean(HelloWorldGateway.class);
        gateway.place("World");

        ctx.close();
    }

    @Bean
    public IntegrationFlow helloWorldFlow(final LocalDateTimeProvider localDateTimeProvider, final OutputStream outputStream) {
        return f -> f
                .transform(String.class, s -> localDateTimeProvider.get() + " Hello " + s + "\n")
                .<String, String>transform(String::toUpperCase)
                .handle(new ByteStreamWritingMessageHandler(outputStream));
    }

    @Bean
    public LocalDateTimeProvider localDateTimeProvider() {
        return new DefaultLocalDateTimeProvider();
    }

    @Bean
    public OutputStream outputStream() {
        return System.out;
    }

    @MessagingGateway
    public interface HelloWorldGateway {

        @Gateway(requestChannel = "helloWorldFlow.input")
        void place(String input);
    }
}
