package cz.profinit.training.springadvanced.springws.vat.endpoint;

import cz.profinit.training.springadvanced.springws.vat.WebServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import java.util.Collections;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebServiceConfig.class)
public class CheckVatEndpointTest {

    private MockWebServiceClient mockClient;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void createClient() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void checkVat1() {
        final StringSource requestPayload = new StringSource(
                "<checkVat xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                        "   <countryCode>CZ</countryCode>\n" +
                        "   <vatNumber>CZ25650203</vatNumber>\n" +
                        "</checkVat>\n");

        final StringSource responsePayload = new StringSource(
                "<checkVatResponse xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                        "   <countryCode>CZ</countryCode>\n" +
                        "   <vatNumber>CZ25650203</vatNumber>\n" +
                        "   <valid>true</valid>\n" +
                        "   <name>Profinit, s.r.o.</name>\n" +
                        "   <address>Tychonova 2, 160 00 Praha 6</address>\n" +
                        "</checkVatResponse>\n");

        // TODO
    }

    @Test
    public void checkVat2() {
        final StringSource requestPayload = new StringSource(
                "<checkVat xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                        "   <countryCode>CZ</countryCode>\n" +
                        "   <vatNumber>CZ25650203</vatNumber>\n" +
                        "</checkVat>\n");

        final Map<String, String> nsMap = Collections.singletonMap("ns", "urn:ec.europa.eu:taxud:vies:services:checkVat:types");

    }
}
