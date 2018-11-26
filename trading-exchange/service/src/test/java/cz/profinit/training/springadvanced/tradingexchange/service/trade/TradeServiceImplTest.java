package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import cz.profinit.training.springadvanced.tradingexchange.TradingExchangeApplication;
import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.OrderRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.TradeRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TradingExchangeApplication.class)
public class TradeServiceImplTest {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldGetTrades() {
        LocalDateTime from = LocalDateTime.now();

        // prepare users and currencies
        User userA = userRepository.save(User.builder().username(Username.of("a")).build());
        User userB = userRepository.save(User.builder().username(Username.of("b")).build());
        Currency eur = currencyRepository.save(Currency.builder().code("EUR").build());
        Currency czk = currencyRepository.save(Currency.builder().code("CZK").build());

        // buy 100 EUR
        Order buyOrder = orderRepository.save(
                Order.buy(null, eur, czk, 100, 26, userA));

        // sell 50 EUR
        Order sellOrder1 = orderRepository.save(
                Order.sell(null, czk, eur, 50, 26, userB));

        // sell 50 EUR
        Order sellOrder2 = orderRepository.save(
                Order.sell(null, czk, eur, 50, 26, userB));

        Trade trade1 = tradeRepository.save(
                Trade.builder()
                        .buyOrder(buyOrder)
                        .sellOrder(sellOrder1)
                        .amount(Money.of(eur, 50))
                        .price(Money.of(czk, 26))
                        .whenCreated(LocalDateTime.now())
                        .build());

        Trade trade2 = tradeRepository.save(
                Trade.builder()
                        .buyOrder(buyOrder)
                        .sellOrder(sellOrder2)
                        .amount(Money.of(eur, 50))
                        .price(Money.of(czk, 26))
                        .whenCreated(LocalDateTime.now())
                        .build());

        List<TradeSummaryTo> trades = tradeService.getTrades(from, LocalDateTime.now());

        assertEquals(2, trades.size());
        assertEquals(trade1.getId(), trades.get(0).getId());
        assertEquals(trade2.getId(), trades.get(1).getId());
    }
}