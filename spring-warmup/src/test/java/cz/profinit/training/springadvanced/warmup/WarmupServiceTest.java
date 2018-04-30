package cz.profinit.training.springadvanced.warmup;

import cz.profinit.training.springadvanced.warmup.support.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WarmupServiceTest.TestConfiguration.class)
public class WarmupServiceTest {

    @Configuration
    public static class TestConfiguration {

        @Bean
        public WarmupService warmupService(final Clock localDateTimeProvider) {
            return new WarmupService(localDateTimeProvider);
        }

        @Bean
        public Clock localDateTimeProvider() {
            return () -> LocalDateTime.of(2011, Month.DECEMBER, 8, 12, 30);
        }
    }

    @Autowired
    private WarmupService service;

    @Test
    public void saysHello() {
        final String hello = service.sayHello("world");
        assertEquals("Hello world at 2011-12-08T12:30", hello);
    }
}
