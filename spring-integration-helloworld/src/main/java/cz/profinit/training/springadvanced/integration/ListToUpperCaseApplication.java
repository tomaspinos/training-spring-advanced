package cz.profinit.training.springadvanced.integration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
@EnableAutoConfiguration
@IntegrationComponentScan
public class ListToUpperCaseApplication {

    public static void main(final String[] args) throws InterruptedException {
        final ConfigurableApplicationContext ctx = SpringApplication.run(ListToUpperCaseApplication.class, args);

        final List<String> strings = Arrays.asList("foo", "bar", "rum", "gin");
        System.out.println(ctx.getBean(Upcase.class).upcase(strings));

        ctx.close();
    }

    @Bean
    public IntegrationFlow upcase() {
        return f -> f
                .split()
                .<String, String>transform(String::toUpperCase)
                .aggregate();
    }

    @MessagingGateway
    public interface Upcase {

        @Gateway(requestChannel = "upcase.input")
        Collection<String> upcase(Collection<String> strings);
    }
}
