package cz.profinit.training.springadvanced.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Transformers;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
@IntegrationComponentScan
public class TelegramLambdaProcess {

    public static void main(String[] args) {
        SpringApplication.run(TelegramLambdaProcess.class, args);
    }

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows
                // MessageSources s
                .from(s -> s.file(new File("c:/temp/telegram-input")).patternFilter("*.txt").preventDuplicates(),
                        e -> e.poller(Pollers.fixedDelay(1000)))
                .transform(Transformers.fileToString())
                .transform(Transformers.<String, String>converter(payload ->
                        Arrays.stream(payload.split(" ")).map(String::toUpperCase).collect(Collectors.joining(" - "))))
                // Adapters a
                .handleWithAdapter(a -> a.file(new File("c:/temp/telegram-output")).deleteSourceFiles(true))
                .get();
    }
}
