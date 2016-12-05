package cz.profinit.training.springadvanced.springws.vat.endpoint;

import cz.profinit.training.springadvanced.springws.vat.repository.CheckVatRepository;
import cz.profinit.training.springadvanced.springws.vat.repository.VatRegistryRecord;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVat;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;

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

    public CheckVatResponse checkVat(final CheckVat request) {
        final String countryCode = request.getCountryCode();
        final String vatNumber = request.getVatNumber();

        VatValidations.validateCountryCode(countryCode);
        VatValidations.validateVatNmber(vatNumber);

        final VatRegistryRecord record = repository.checkVat(countryCode, vatNumber);

        // TODO Construct response from the record

        return null;
    }
}
