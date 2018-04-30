package cz.profinit.training.springadvanced.warmup;

import cz.profinit.training.springadvanced.warmup.support.Clock;
import cz.profinit.training.springadvanced.warmup.support.DefaultClock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WarmupConfiguration {

    @Bean
    public WarmupService warmupService(final Clock localDateTimeProvider) {
        return new WarmupService(localDateTimeProvider);
    }

    @Bean
    public Clock localDateTimeProvider() {
        return new DefaultClock();
    }
}
