package cz.profinit.training.springadvanced.springws.vat.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import cz.profinit.training.springadvanced.springws.vat.repository.CheckVatRepository;
import cz.profinit.training.springadvanced.springws.vat.repository.VatRegistryRecord;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVat;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import eu.europa.ec.taxud.vies.services.checkvat.types.ObjectFactory;

/**
 * TODO Annotate the class and methods properly
 */
@Endpoint
public class CheckVatEndpoint {

    private final CheckVatRepository repository;

    @Autowired
    public CheckVatEndpoint(final CheckVatRepository repository) {
        this.repository = repository;
    }

    @PayloadRoot(namespace = "urn:ec.europa.eu:taxud:vies:services:checkVat:types", localPart = "checkVat")
    @ResponsePayload
    public CheckVatResponse checkVat(@RequestPayload CheckVat request) {
        VatRegistryRecord record = repository.checkVat(request.getCountryCode(), request.getVatNumber());

        // TODO Construct the response

        ObjectFactory objectFactory = new ObjectFactory();

        CheckVatResponse response = objectFactory.createCheckVatResponse();
        response.setCountryCode(record.getCountryCode());
        response.setVatNumber(record.getVatNumber());
        response.setValid(true);
        response.setName(objectFactory.createCheckVatResponseName(record.getName()));
        response.setAddress(objectFactory.createCheckVatResponseAddress(record.getAddress()));

        return response;
    }
}
