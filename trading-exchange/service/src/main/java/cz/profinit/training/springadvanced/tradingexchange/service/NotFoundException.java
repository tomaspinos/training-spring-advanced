package cz.profinit.training.springadvanced.tradingexchange.service;

import lombok.Getter;

public class NotFoundException extends RuntimeException {

    @Getter
    private final Class<?> entityType;
    @Getter
    private final String value;

    public NotFoundException(Class<?> entityType, Long id) {
        this.entityType = entityType;
        this.value = Long.toString(id);
    }

    public NotFoundException(Class<?> entityType, String value) {
        this.entityType = entityType;
        this.value = value;
    }
}
