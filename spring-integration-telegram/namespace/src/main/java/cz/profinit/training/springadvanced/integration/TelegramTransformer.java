package cz.profinit.training.springadvanced.integration;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TelegramTransformer extends AbstractPayloadTransformer<String, String> {

    @Override
    protected String transformPayload(String payload) throws Exception {
        return Arrays.stream(payload.split(" ")).map(String::toUpperCase).collect(Collectors.joining(" - "));
    }
}
