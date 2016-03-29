package cz.profinit.training.springadvanced.springws.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class WsClient {

    private static final String MESSAGE =
            "<urn:checkVat xmlns:urn=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                    "<urn:countryCode>CZ</urn:countryCode>\n" +
                    "<urn:vatNumber>CZ25650203</urn:vatNumber>\n" +
                    "</urn:checkVat>";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ClientConfiguration.class);

        WebServiceTemplate template = context.getBean(WebServiceTemplate.class);

        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);
        template.sendSourceAndReceiveToResult(source, result);
    }
}
