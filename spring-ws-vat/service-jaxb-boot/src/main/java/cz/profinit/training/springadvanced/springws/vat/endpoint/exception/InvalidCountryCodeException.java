package cz.profinit.training.springadvanced.springws.vat.endpoint.exception;

public class InvalidCountryCodeException extends RuntimeException {

    public InvalidCountryCodeException(final String countryCode) {
        super("Invalid country code: " + countryCode);
    }
}
