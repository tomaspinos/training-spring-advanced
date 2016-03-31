package cz.profinit.training.springadvanced.springws.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public Client client() {
        Client client = new Client();
        client.setDefaultUri("http://localhost:8080");
        return client;
    }
}
