package cz.profinit.training.springadvanced.springws.vat.repository;

/**
 * @author tpinos@csob.cz Tomas Pinos (JD71691)
 */
public class VatRegistryRecord {

    private final String countryCode;
    private final String vatNumber;
    private final String name;
    private final String address;

    public VatRegistryRecord(String countryCode, String vatNumber, String name, String address) {
        this.countryCode = countryCode;
        this.vatNumber = vatNumber;
        this.name = name;
        this.address = address;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
