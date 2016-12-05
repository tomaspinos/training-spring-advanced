package cz.profinit.training.springadvanced.integration.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;

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
    public IntegrationFlow helloWorldFlow(final OutputStream outputStream) {
        // TODO return f -> f...
        return null;
    }

    @Bean
    public OutputStream outputStream() {
        return System.out;
    }

    /**
     * TODO Annotate gateway class and method.
     */
    public interface HelloWorldGateway {

        void place(String input);
    }
}
