package cz.profinit.training.springadvanced.springws.vat.endpoint;

import cz.profinit.training.springadvanced.springws.vat.repository.CheckVatRepository;

/**
 * TODO Implement checkVat method
 * TODO Annotate the class and methods properly
 */
public class CheckVatEndpoint {

    private final CheckVatRepository repository;

    public CheckVatEndpoint(final CheckVatRepository repository) {
        this.repository = repository;
    }

}
