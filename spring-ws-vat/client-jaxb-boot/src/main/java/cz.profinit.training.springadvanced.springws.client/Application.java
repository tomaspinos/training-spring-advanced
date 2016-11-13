package cz.profinit.training.springadvanced.springws.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    CommandLineRunner run(final Client client) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                String vat = args.length > 0 ? args[0] : "CZ25650203";

                client.checkVat(vat);
            }
        };
    }
}
