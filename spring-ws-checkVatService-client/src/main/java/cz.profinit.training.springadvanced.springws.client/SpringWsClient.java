package cz.profinit.training.springadvanced.springws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;

/**
 * Hello world!
 */
public class SpringWsClient {

    private static final String MESSAGE =
            "<urn:checkVat xmlns:urn=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                    "<urn:countryCode>CZ</urn:countryCode>\n" +
                    "<urn:vatNumber>CZ25650203</urn:vatNumber>\n" +
                    "</urn:checkVat>";

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("springws-client-context.xml");

        WebServiceTemplate wst = context.getBean("webServiceTemplate", WebServiceTemplate.class);

        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);
        wst.sendSourceAndReceiveToResult(source, result);
    }
}
