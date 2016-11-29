package cz.profinit.training.springadvanced.springws.vat.endpoint;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class InvalidVatNumberException extends RuntimeException {

    public InvalidVatNumberException(final String vatNumber) {
        super("Invalid vat number: " + vatNumber);
    }
}
