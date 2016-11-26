package cz.profinit.training.springadvanced.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TelegramLambdaProcessTest {

    private static final String INPUT_FOLDER = "c:/temp/test-telegram-input";
    private static final String OUTPUT_FOLDER = "c:/temp/test-telegram-output";

    @Configuration
    public static class TestConfiguration {
    }

    @Value("classpath*:/input/*.txt")
    private Resource[] inputFiles;

    @Before
    public void setUp() throws Exception {
        createFolder(INPUT_FOLDER);
        createFolder(OUTPUT_FOLDER);
    }

    @Test
    public void testCompleteFlow() throws Exception {
        List<String> lines = copyInputFilesAndReadAllLines();

        TelegramLambdaProcess.main(new String[]{"--input=" + INPUT_FOLDER, "--output=" + OUTPUT_FOLDER});

        Thread.sleep(2000);

        verifyTelegramsHasBeenProcessed(lines);
    }

    private void verifyTelegramsHasBeenProcessed(List<String> lines) throws Exception {
        final int[] telegramCount = {0};

        Files.walk(Paths.get(OUTPUT_FOLDER)).forEach(path -> {
            try {
                if (!Files.isDirectory(path)) {
                    assertEquals("Each output file contains 1 telegram", 1, Files.readAllLines(path).size());
                    telegramCount[0]++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        assertEquals("Not all telegrams processed", lines.size(), telegramCount[0]);
    }

    private List<String> copyInputFilesAndReadAllLines() throws IOException {
        List<String> allLines = new ArrayList<>();

        for (Resource inputFile : inputFiles) {
            List<String> lines = Files.readAllLines(Paths.get(inputFile.getURI()));
            allLines.addAll(lines);

            Files.write(Paths.get(INPUT_FOLDER, inputFile.getFilename()), lines);
        }

        return allLines;
    }

    private void createFolder(String folderPath) throws Exception {
        Path path = Paths.get(folderPath);
        if (Files.exists(path)) {
            Files.walk(path).forEach(p -> {
                try {
                    if (!Files.isDirectory(p)) {
                        Files.delete(p);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Files.delete(path);
        }
        Files.createDirectories(path);
    }
}
