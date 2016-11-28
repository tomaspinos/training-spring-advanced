package cz.profinit.training.springadvanced.springws.vat.endpoint;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.springframework.xml.transform.StringSource;

import cz.profinit.training.springadvanced.springws.vat.WebServiceConfig;

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

        mockClient.sendRequest(RequestCreators.withPayload(requestPayload))
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void checkVat2() {
        final StringSource requestPayload = new StringSource(
                "<checkVat xmlns=\"urn:ec.europa.eu:taxud:vies:services:checkVat:types\">\n" +
                        "   <countryCode>CZ</countryCode>\n" +
                        "   <vatNumber>CZ25650203</vatNumber>\n" +
                        "</checkVat>\n");

        final Map<String, String> nsMap = Collections.singletonMap("ns", "urn:ec.europa.eu:taxud:vies:services:checkVat:types");

        mockClient.sendRequest(RequestCreators.withPayload(requestPayload))
                .andExpect(ResponseMatchers.noFault())
                .andExpect(ResponseMatchers.xpath("/ns:checkVatResponse", nsMap).exists())
                .andExpect(ResponseMatchers.xpath("//ns:countryCode", nsMap).evaluatesTo("CZ"))
                .andExpect(ResponseMatchers.xpath("//ns:vatNumber", nsMap).evaluatesTo("CZ25650203"))
                .andExpect(ResponseMatchers.xpath("//ns:valid", nsMap).evaluatesTo(true))
                .andExpect(ResponseMatchers.xpath("//ns:name", nsMap).evaluatesTo("Profinit, s.r.o."))
                .andExpect(ResponseMatchers.xpath("//ns:address", nsMap).evaluatesTo("Tychonova 2, 160 00 Praha 6"))
                .andExpect(ResponseMatchers.xpath("//ns:requestDate", nsMap).doesNotExist());
    }
}
