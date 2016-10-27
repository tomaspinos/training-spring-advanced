package cz.profinit.training.springadvanced.springws.vat.repository;

public class VatRegistryRecord {

    private final String countryCode;
    private final String vatNumber;
    private final String name;
    private final String address;

    public VatRegistryRecord(final String countryCode,
                             final String vatNumber,
                             final String name,
                             final String address) {

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
