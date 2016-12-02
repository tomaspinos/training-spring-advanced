package cz.profinit.training.springadvanced.warmup;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.profinit.training.springadvanced.warmup.support.LocalDateTimeProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WarmupConfiguration.class, WarmupServiceTest.TestConfiguration.class})
public class WarmupServiceTest {

    @Configuration
    public static class TestConfiguration {

        @Bean
        public LocalDateTimeProvider localDateTimeProvider() {
            return () -> LocalDateTime.of(2011, Month.DECEMBER, 8, 12, 30);
        }
    }

    @Autowired
    private WarmupService service;

    @Test
    public void saysHello() {
        final String hello = service.sayHello("world");
        Assert.assertEquals("Hello world at 2011-12-08T12:30", hello);
    }
}
