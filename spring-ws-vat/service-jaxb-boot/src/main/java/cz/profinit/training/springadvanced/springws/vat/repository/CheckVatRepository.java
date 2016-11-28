package cz.profinit.training.springadvanced.springws.vat.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CheckVatRepository {

    public VatRegistryRecord checkVat(final String countryCode, final String vatNumber) {
        return new VatRegistryRecord(countryCode, vatNumber, "Profinit, s.r.o.", "Tychonova 2, 160 00 Praha 6");
    }
}
