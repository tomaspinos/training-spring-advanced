package cz.profinit.training.springadvanced.springws.client;

import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
@Component
public class Client extends WebServiceGatewaySupport {

    private static final String MESSAGE =
            "<urn:checkVat xmlns:urn=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                    "<urn:countryCode>CZ</urn:countryCode>\n" +
                    "<urn:vatNumber>$VAT</urn:vatNumber>\n" +
                    "</urn:checkVat>";

    public void checkVat(String vat) {
        StreamSource source = new StreamSource(new StringReader(MESSAGE.replace("$VAT", vat)));
        StreamResult result = new StreamResult(System.out);
        getWebServiceTemplate().sendSourceAndReceiveToResult(source, result);
    }
}
