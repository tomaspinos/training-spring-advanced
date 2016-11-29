package cz.profinit.training.springadvanced.springws.vat.endpoint.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class InvalidCountryCodeException extends RuntimeException {

    public InvalidCountryCodeException(final String countryCode) {
        super("Invalid country code: " + countryCode);
    }
}
