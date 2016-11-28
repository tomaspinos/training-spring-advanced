package cz.profinit.training.springadvanced.springws.client;

import java.io.StringReader;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Hello world!
 */
public class Client {

    private static final String MESSAGE =
            "<urn:checkVat xmlns:urn=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                    "<urn:countryCode>CZ</urn:countryCode>\n" +
                    "<urn:vatNumber>CZ25650203</urn:vatNumber>\n" +
                    "</urn:checkVat>";

    public static void main(final String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("springws-client-context.xml");

        // TODO Obtain the WebServiceTemplate from the application context
        // TODO Implement the service call

        final WebServiceTemplate template = context.getBean(WebServiceTemplate.class);

        final StreamSource source = new StreamSource(new StringReader(MESSAGE));
        final StreamResult result = new StreamResult(System.out);
        template.sendSourceAndReceiveToResult(source, result);
    }
}
