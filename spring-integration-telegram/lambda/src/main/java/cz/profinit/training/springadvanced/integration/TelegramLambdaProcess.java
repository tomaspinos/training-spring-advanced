package cz.profinit.training.springadvanced.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.splitter.DefaultMessageSplitter;
import org.springframework.messaging.MessageHeaders;

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
    public IntegrationFlow integrationFlow(FlowConfiguration configuration) {
        DefaultMessageSplitter splitter = new DefaultMessageSplitter();
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
                                new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date())
                                        + "." + m.getHeaders().get(MessageHeaders.ID) + ".txt"))
                .get();
    }

    @Bean
    public FlowConfiguration configuration() {
        return new FlowConfiguration("c:/temp/telegram-input", "c:/temp/telegram-output");
    }

    public static class FlowConfiguration {

        private final String inputFolder;
        private final String outputFolder;

        public FlowConfiguration(String inputFolder, String outputFolder) {
            this.inputFolder = inputFolder;
            this.outputFolder = outputFolder;
        }

        public String getInputFolder() {
            return inputFolder;
        }

        public String getOutputFolder() {
            return outputFolder;
        }
    }
}
