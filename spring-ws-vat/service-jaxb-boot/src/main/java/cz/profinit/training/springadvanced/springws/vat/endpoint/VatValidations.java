package cz.profinit.training.springadvanced.springws.vat.endpoint;

import org.springframework.util.StringUtils;

import cz.profinit.training.springadvanced.springws.vat.endpoint.exception.InvalidCountryCodeException;
import cz.profinit.training.springadvanced.springws.vat.endpoint.exception.InvalidVatNumberException;

public final class VatValidations {

    public VatValidations() {
        throw new UnsupportedOperationException();
    }

    public static void validateCountryCode(final String countryCode) throws InvalidCountryCodeException {
        if (!StringUtils.hasText(countryCode)) {
            throw new InvalidCountryCodeException(countryCode);
        }

        if (!countryCode.equals("CZ")) {
            throw new InvalidCountryCodeException(countryCode);
        }
    }

    public static void validateVatNmber(final String vatNumber) throws InvalidVatNumberException {
        if (!StringUtils.hasText(vatNumber)) {
            throw new InvalidVatNumberException(vatNumber);
        }

        if (!vatNumber.startsWith("CZ")) {
            throw new InvalidVatNumberException(vatNumber);
        }

        try {
            Integer.parseInt(vatNumber.substring(2));
        } catch (final NumberFormatException e) {
            throw new InvalidVatNumberException(vatNumber);
        }
    }
}
