package cz.profinit.training.springadvanced.springws.client;

import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatRequest;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

@Component
public class Client extends WebServiceGatewaySupport {

    public void checkVat(final String vat) {
        // TODO Get WebServiceTemplate from the parent class
        // TODO Implement the service call

        final CheckVatRequest request = new CheckVatRequest();
        request.setCountryCode("CZ");
        request.setVatNumber(vat);

        final CheckVatResponse response = (CheckVatResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        System.out.println("Name: " + response.getName());
        System.out.println("Address: " + response.getAddress());
    }
}
