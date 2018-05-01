package cz.profinit.training.springadvanced.integration.helloworld;

import cz.profinit.training.springadvanced.integration.support.Clock;
import cz.profinit.training.springadvanced.integration.support.DefaultClock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.stream.ByteStreamWritingMessageHandler;

import java.io.OutputStream;

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

    /**
     * "helloWorldFlow.input" = the input channel
     */
    @Bean
    public IntegrationFlow helloWorldFlow(final Clock clock, final OutputStream outputStream) {
        return f -> f
                .transform(String.class, s -> clock.get() + " Hello " + s + "\n")
                .<String, String>transform(String::toUpperCase)
                .handle(new ByteStreamWritingMessageHandler(outputStream));
    }

    @Bean
    public Clock clock() {
        return new DefaultClock();
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
