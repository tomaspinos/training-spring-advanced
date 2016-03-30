package cz.profinit.training.springadvanced.springws.service;

import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVat;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import eu.europa.ec.taxud.vies.services.checkvat.types.ObjectFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
@Endpoint
public class CheckVatServiceEndpoint {

    @PayloadRoot(namespace = "urn:ec.europa.eu:taxud:vies:services:checkVat:types", localPart = "checkVat")
    @ResponsePayload
    public CheckVatResponse checkVat(@RequestPayload CheckVat request) {
        ObjectFactory objectFactory = new ObjectFactory();

        CheckVatResponse response = objectFactory.createCheckVatResponse();
        response.setCountryCode(request.getCountryCode());
        response.setVatNumber(request.getVatNumber());
        response.setRequestDate(getDatatypeFactory().newXMLGregorianCalendar());
        response.setValid(true);
        response.setName(objectFactory.createCheckVatResponseName("Profinit, s.r.o."));
        response.setAddress(objectFactory.createCheckVatResponseAddress("Tychonova 2, 160 00 Praha 6"));

        return response;
    }

    protected DatatypeFactory getDatatypeFactory() {
        try {
            return DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Internal error");
        }
    }
}
