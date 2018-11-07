package cz.profinit.training.springadvanced.integration;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

public class TelegramTransformer extends AbstractPayloadTransformer<String, String> {

    @Override
    protected String transformPayload(final String payload) throws Exception {
        return Arrays.stream(payload.split(" ")).map(String::toUpperCase).collect(Collectors.joining(" - "));
    }
}
