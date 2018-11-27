package cz.profinit.training.springadvanced.tradingexchange.domain;

public enum OrderType {

    /**
     * E.g. Buy 100 EUR for max 26 CZK.
     */
    BUY,
    /**
     * Sell 100 EUR for min 25 CZK.
     */
    SELL
}
