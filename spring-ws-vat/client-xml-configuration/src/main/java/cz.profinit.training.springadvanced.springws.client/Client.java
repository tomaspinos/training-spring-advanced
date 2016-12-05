package cz.profinit.training.springadvanced.springws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    }
}
