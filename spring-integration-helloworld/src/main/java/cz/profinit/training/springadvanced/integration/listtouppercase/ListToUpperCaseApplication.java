package cz.profinit.training.springadvanced.integration.listtouppercase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
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
        // TODO return f -> f...
        // TODO use split() and aggregate()
        return null;
    }

    /**
     * TODO Annotate gateway class and method.
     */
    public interface UpcaseGateway {

        Collection<String> upcase(Collection<String> strings);
    }
}
