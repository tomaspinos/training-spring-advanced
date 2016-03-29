package cz.profinit.training.springadvanced.springws.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class ClientConfiguration {

    @Bean
    public WebServiceTemplate getTemplate() {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setDefaultUri("http://localhost:8080");
        return template;
    }
}
