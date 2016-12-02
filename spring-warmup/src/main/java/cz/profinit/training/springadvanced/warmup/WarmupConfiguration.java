package cz.profinit.training.springadvanced.warmup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cz.profinit.training.springadvanced.warmup.support.DefaultLocalDateTimeProvider;
import cz.profinit.training.springadvanced.warmup.support.LocalDateTimeProvider;

@Configuration
public class WarmupConfiguration {

    @Bean
    public WarmupService warmupService(final LocalDateTimeProvider localDateTimeProvider) {
        return new WarmupService(localDateTimeProvider);
    }

    @Bean
    public LocalDateTimeProvider localDateTimeProvider() {
        return new DefaultLocalDateTimeProvider();
    }
}
