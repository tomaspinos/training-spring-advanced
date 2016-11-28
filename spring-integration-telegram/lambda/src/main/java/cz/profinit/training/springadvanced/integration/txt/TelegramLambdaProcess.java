package cz.profinit.training.springadvanced.integration.txt;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.messaging.MessageHeaders;

@SpringBootApplication
@IntegrationComponentScan
public class TelegramLambdaProcess {

    public static void main(final String[] args) {
        SpringApplication.run(TelegramLambdaProcess.class, args);
    }

    @Bean
    public IntegrationFlow integrationFlow(final FlowConfiguration configuration) {
        final DefaultMessageSplitter splitter = new DefaultMessageSplitter();
        splitter.setDelimiters("\r\n");

        return IntegrationFlows
                // MessageSourcesFunction sources, Consumer<SourcePollingChannelAdapterSpec> endpointConfigurer
                // MessageSources s
                .from(s -> s.file(new File(configuration.getInputFolder()))
                                .patternFilter("*.txt")
                                .preventDuplicates(),
                        e -> e.poller(Pollers.fixedDelay(1000)))
                .transform(Transformers.fileToString())
                .split(splitter)
                .transform(Transformers.<String, String>converter(payload ->
                        Arrays.stream(payload.split(" "))
                                .map(String::toUpperCase)
                                .collect(Collectors.joining(" - "))))
                // Function<Adapters, MessageHandlerSpec<?, H>> adapters
                // Adapters a
                .handleWithAdapter(a -> a.file(new File(configuration.getOutputFolder()))
                        .deleteSourceFiles(true)
                        .fileNameGenerator(m ->
                                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
                                        + "." + m.getHeaders().get(MessageHeaders.ID) + ".txt"))
                .get();
    }

    @Bean
    public FlowConfiguration configuration(final Environment environment) {
        return new FlowConfiguration(
                environment.getProperty("input", "c:/temp/telegram-input"),
                environment.getProperty("output", "c:/temp/telegram-output"));
    }

    private static class FlowConfiguration {

        private final String inputFolder;
        private final String outputFolder;

        private FlowConfiguration(final String inputFolder, final String outputFolder) {
            this.inputFolder = inputFolder;
            this.outputFolder = outputFolder;
        }

        private String getInputFolder() {
            return inputFolder;
        }

        private String getOutputFolder() {
            return outputFolder;
        }
    }
}
