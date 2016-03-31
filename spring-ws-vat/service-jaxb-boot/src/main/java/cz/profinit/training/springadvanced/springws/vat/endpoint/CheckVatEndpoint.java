package cz.profinit.training.springadvanced.springws.vat.endpoint;

import cz.profinit.training.springadvanced.springws.vat.repository.CheckVatRepository;
import cz.profinit.training.springadvanced.springws.vat.repository.VatRegistryRecord;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVat;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import eu.europa.ec.taxud.vies.services.checkvat.types.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CheckVatEndpoint {

    @Autowired
    private CheckVatRepository repository;

    @PayloadRoot(namespace = "urn:ec.europa.eu:taxud:vies:services:checkVat:types", localPart = "checkVat")
    @ResponsePayload
    public CheckVatResponse checkVat(@RequestPayload CheckVat request) {
        VatRegistryRecord record = repository.checkVat(request.getCountryCode(), request.getVatNumber());

        ObjectFactory objectFactory = new ObjectFactory();

        CheckVatResponse response = objectFactory.createCheckVatResponse();
        response.setCountryCode(record.getCountryCode());
        response.setVatNumber(record.getVatNumber());
        response.setRequestDate(getDatatypeFactory().newXMLGregorianCalendar());
        response.setValid(true);
        response.setName(objectFactory.createCheckVatResponseName(record.getName()));
        response.setAddress(objectFactory.createCheckVatResponseAddress(record.getName()));

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
