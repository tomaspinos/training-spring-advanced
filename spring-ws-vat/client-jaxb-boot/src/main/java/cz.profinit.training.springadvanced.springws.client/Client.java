package cz.profinit.training.springadvanced.springws.client;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Component
public class Client extends WebServiceGatewaySupport {

    public void checkVat(final String vat) {
        // TODO Get WebServiceTemplate from the parent class
        // TODO Implement the service call
    }
}
