package cz.profinit.training.springadvanced.integration;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import cz.profinit.training.springadvanced.integration.support.LocalDateTimeProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HelloWorldApplication.class, HelloworldApplicationTests.TestConfiguration.class})
@Configuration
public class HelloworldApplicationTests {

    public static class TestConfiguration {

        @Bean
        public LocalDateTimeProvider localDateTimeProvider() {
            return () -> LocalDateTime.of(2011, Month.DECEMBER, 8, 12, 30);
        }

        @Bean
        public OutputStream outputStream() {
            return outputStream;
        }
    }

    static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	@Autowired
	private HelloWorldApplication.HelloWorldGateway helloWorldGateway;

    @Before
    public void setUp() throws Exception {
        outputStream.reset();
    }

    @Test
	public void flowDecoratesInputProperly() {
		helloWorldGateway.place("123");

        Assert.assertEquals("2011-12-08T12:30 HELLO 123\n", outputStream.toString());
	}
}
