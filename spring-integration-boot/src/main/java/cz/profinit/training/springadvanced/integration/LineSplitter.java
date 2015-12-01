package cz.profinit.training.springadvanced.integration;

import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;

public class LineSplitter extends AbstractMessageSplitter {

    @Override
    protected Object splitMessage(Message<?> message) {
        String payload = (String) message.getPayload();
        return payload.split("\n");
    }
}
