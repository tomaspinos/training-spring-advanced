package cz.profinit.training.springadvanced.tradingexchange;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class TradingExchangeClient {

    private final String url;
    private final RestTemplate restTemplate = new RestTemplate();

    public void createUser(String username) {
        restTemplate.postForLocation(url + "/user/{username}", null, username);
    }

    public void deposit(String username, String currencyCode, long amount) {
        deposit(username, currencyCode, new BigDecimal(amount));
    }

    public void deposit(String username, String currencyCode, BigDecimal amount) {
        restTemplate.put(url + "/user/{username}/balance/{currencyCode}/{amount}", null, username, currencyCode, amount);
    }

    public void buy(String username, String requestedCurrencyCode, String offeredCurrencyCode, long amount, long maxPrice) {
        buy(username, requestedCurrencyCode, offeredCurrencyCode, new BigDecimal(amount), new BigDecimal(maxPrice));
    }

    public void buy(String username, String requestedCurrencyCode, String offeredCurrencyCode, BigDecimal amount, BigDecimal maxPrice) {
        restTemplate.postForLocation(url + "/order/buy/{username}/{requestedCurrencyCode}/{offeredCurrencyCode}/{amount}/{maxPrice}",
                null, username, requestedCurrencyCode, offeredCurrencyCode, amount, maxPrice);
    }

    public void sell(String username, String requestedCurrencyCode, String offeredCurrencyCode, long amount, long minPrice) {
        sell(username, requestedCurrencyCode, offeredCurrencyCode, new BigDecimal(amount), new BigDecimal(minPrice));
    }

    public void sell(String username, String requestedCurrencyCode, String offeredCurrencyCode, BigDecimal amount, BigDecimal minPrice) {
        restTemplate.postForLocation(url + "/order/sell/{username}/{requestedCurrencyCode}/{offeredCurrencyCode}/{amount}/{minPrice}",
                null, username, requestedCurrencyCode, offeredCurrencyCode, amount, minPrice);
    }
}
