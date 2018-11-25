package cz.profinit.training.springadvanced.tradingexchange;

import java.util.stream.IntStream;

public class TradingExchangeTest {

    public static final String JOHN = "john";
    public static final String SAM = "sam";
    public static final String RON = "ron";
    public static final String EUR = "EUR";
    public static final String CZK = "CZK";

    public static void main(String[] args) {
        new TradingExchangeTest().executeScenario();
    }

    private void executeScenario() {
        TradingExchangeClient client = new TradingExchangeClient("http://localhost:8080");

        client.createUser(JOHN);
        client.createUser(SAM);
        client.createUser(RON);

        client.deposit(JOHN, EUR, 1000000);
        client.deposit(SAM, CZK, 26000000);
        client.deposit(RON, CZK, 26000000);

        IntStream.range(0, 10000).forEach(i -> {
            // sell 1000 EUR
            client.sell(JOHN, CZK, EUR, 2, 26);
            // buy 500 EUR
            client.buy(SAM, EUR, CZK, 1, 26);
            // buy 500 EUR
            client.buy(RON, EUR, CZK, 1, 26);
        });
    }
}
