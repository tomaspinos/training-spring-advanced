package cz.profinit.training.springadvanced.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TelegramLambdaProcess.class, TelegramLambdaProcessTest.TestConfiguration.class})
public class TelegramLambdaProcessTest {

    @Autowired
    private TelegramLambdaProcess.FlowConfiguration configuration;

    @Configuration
    public static class TestConfiguration {

        @Bean
        public TelegramLambdaProcess.FlowConfiguration configuration() {
            return new TelegramLambdaProcess.FlowConfiguration("c:/temp/test-telegram-input", "c:/temp/test-telegram-output");
        }
    }

    @Before
    public void setUp() {
        createFolder(configuration.getInputFolder());
        createFolder(configuration.getOutputFolder());
    }

    @Test
    public void testCompleteFlow() {
    }

    private void createFolder(String path) {
        File folder = new File(path);
        if (folder.exists()) {
            folder.delete();
        }
        folder.mkdirs();
    }
}
