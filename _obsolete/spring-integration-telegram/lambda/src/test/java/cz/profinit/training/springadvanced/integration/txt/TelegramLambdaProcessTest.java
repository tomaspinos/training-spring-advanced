package cz.profinit.training.springadvanced.integration.txt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

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
        createFolder(Paths.get(INPUT_FOLDER));
        createFolder(Paths.get(OUTPUT_FOLDER));
    }

    @Test
    public void testCompleteFlow() throws Exception {
        final List<String> lines = copyInputFilesAndReadAllLines();

        TelegramLambdaProcess.main(new String[]{"--input=" + INPUT_FOLDER, "--output=" + OUTPUT_FOLDER});

        Thread.sleep(2000);

        verifyTelegramsHasBeenProcessed(lines);
    }

    private void verifyTelegramsHasBeenProcessed(final List<String> lines) throws Exception {
        final AtomicInteger telegramCount = new AtomicInteger(0);

        withTelegramFiles(path -> {
            try {
                assertEquals("Each output file contains 1 telegram", 1, Files.lines(path).count());
                telegramCount.incrementAndGet();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        });

        assertEquals("Not all telegrams processed", lines.size(), telegramCount.get());
    }

    private List<String> copyInputFilesAndReadAllLines() throws IOException {
        final List<String> allLines = new ArrayList<>();

        for (final Resource inputFile : inputFiles) {
            final List<String> lines = Files.readAllLines(Paths.get(inputFile.getURI()));
            allLines.addAll(lines);

            Files.write(Paths.get(INPUT_FOLDER, inputFile.getFilename()), lines);
        }

        return allLines;
    }

    private void withTelegramFiles(final Consumer<Path> consumer) throws IOException {
        Files.walk(Paths.get(OUTPUT_FOLDER)).filter(path -> !Files.isDirectory(path)).forEach(consumer);
    }

    private void createFolder(final Path path) throws Exception {
        if (Files.exists(path)) {
            deleteFolder(path);
        }
        Files.createDirectories(path);
    }

    private void deleteFolder(final Path path) throws IOException {
        Files.walk(path).filter(p -> !Files.isDirectory(p)).forEach(p -> {
            try {
                Files.delete(p);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        });
        Files.delete(path);
    }
}
