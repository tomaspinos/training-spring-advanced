package cz.profinit.training.springadvanced.integration;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * @see <a href="http://docs.spring.io/spring-integration/reference/htmlsingle/#channel-interceptors">Channel Interceptors</a>
 */
public class CountingChannelInterceptor extends ChannelInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(CountingChannelInterceptor.class);

    private final AtomicInteger sendCount = new AtomicInteger();

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        sendCount.incrementAndGet();
        logger.info("Messages sent: " + sendCount.get());
        return message;
    }
}
