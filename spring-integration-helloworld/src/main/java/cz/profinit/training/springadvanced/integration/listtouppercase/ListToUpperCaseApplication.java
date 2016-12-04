package cz.profinit.training.springadvanced.integration.listtouppercase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@IntegrationComponentScan
public class ListToUpperCaseApplication {

    public static void main(final String[] args) throws InterruptedException {
        final ConfigurableApplicationContext ctx = SpringApplication.run(ListToUpperCaseApplication.class, args);

        final List<String> strings = Arrays.asList("foo", "bar", "rum", "gin");
        System.out.println(ctx.getBean(UpcaseGateway.class).upcase(strings));

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
    public interface UpcaseGateway {

        @Gateway(requestChannel = "upcase.input")
        Collection<String> upcase(Collection<String> strings);
    }
}
