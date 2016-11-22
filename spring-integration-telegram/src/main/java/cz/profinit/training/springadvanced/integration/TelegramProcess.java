package cz.profinit.training.springadvanced.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class TelegramProcess {

    public static void main(String[] args) {
       SpringApplication.run(TelegramProcess.class, args);
    }

//    @Bean
//    public IntegrationFlow integrationFlow() {
//        return IntegrationFlows
//                .from(s -> s.file(new File("c://temp/telegram-input")).patternFilter("*.txt"), e -> e.poller(Pollers.fixedDelay(1000)))
//                .transform(Transformers.fileToString())
//                .channel("processFileChannel")
//                .get();
//    }
}
