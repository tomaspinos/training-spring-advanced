package cz.profinit.training.springadvanced.springws.vat.repository;

import org.springframework.stereotype.Repository;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
@Repository
public class CheckVatRepository {

    public VatRegistryRecord checkVat(String countryCode, String vatNumber) {
        return new VatRegistryRecord(countryCode, vatNumber, "Profinit, s.r.o.", "Tychonova 2, 160 00 Praha 6");
    }
}
