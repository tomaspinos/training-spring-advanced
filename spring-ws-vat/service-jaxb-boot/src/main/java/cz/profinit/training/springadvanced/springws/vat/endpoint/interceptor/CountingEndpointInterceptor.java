package cz.profinit.training.springadvanced.springws.vat.endpoint.interceptor;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.interceptor.EndpointInterceptorAdapter;

public class CountingEndpointInterceptor extends EndpointInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(CountingEndpointInterceptor.class);

    private final AtomicInteger requestCount = new AtomicInteger(0);
    private final AtomicInteger responseCount = new AtomicInteger(0);
    private final AtomicInteger faultCount = new AtomicInteger(0);

    @Override
    public boolean handleRequest(final MessageContext messageContext, final Object endpoint) throws Exception {
        requestCount.incrementAndGet();
        logger.info("Requests sent: " + requestCount.get());
        return true;
    }

    @Override
    public boolean handleResponse(final MessageContext messageContext, final Object endpoint) throws Exception {
        responseCount.incrementAndGet();
        logger.info("Responses returned: " + responseCount.get());
        return true;
    }

    @Override
    public boolean handleFault(final MessageContext messageContext, final Object endpoint) throws Exception {
        faultCount.incrementAndGet();
        logger.info("Faults returned: " + faultCount.get());
        return true;
    }
}
