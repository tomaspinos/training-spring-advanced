package cz.profinit.training.springadvanced.springws.vat.endpoint.exception;

public class InvalidVatNumberException extends RuntimeException {

    public InvalidVatNumberException(final String vatNumber) {
        super("Invalid vat number: " + vatNumber);
    }
}
