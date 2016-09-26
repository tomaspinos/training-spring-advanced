package cz.profinit.training.springadvanced.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
@EnableIntegration
public class FlowConfiguration {

    @Bean
    public IntegrationFlow helloWorldFlow() {
        return f -> f
                .transform(String.class, s -> "Hello " + s);
    }
}
