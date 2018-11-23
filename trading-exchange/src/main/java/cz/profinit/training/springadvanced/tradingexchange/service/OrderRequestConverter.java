package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.CurrencyPair;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.UserId;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyPairRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderRequestConverter {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyPairRepository currencyPairRepository;

    private Currency toCurrency(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(Currency.class, code));
    }

    private CurrencyPair toCurrencyPair(Currency firstCurrency, Currency secondCurrency) {
        return currencyPairRepository.findByFirstCurrencyAndSecondCurrency(firstCurrency, secondCurrency)
                .orElseThrow(() -> new NotFoundException(CurrencyPair.class, firstCurrency.getCode() + "-" + secondCurrency.getCode()));
    }

    Order toOrder(BuyOrderRequestTo request) {
        Currency firstCurrency = toCurrency(request.getCurrencyPair().getFirstCurrency().getCode());
        Currency secondCurrency = toCurrency(request.getCurrencyPair().getSecondCurrency().getCode());
        CurrencyPair currencyPair = toCurrencyPair(firstCurrency, secondCurrency);

        Money requestedAmount = Money.builder()
                .currency(secondCurrency)
                .amount(request.getRequestedAmount().getAmount())
                .build();

        Money priceLimit = Money.builder()
                .currency(firstCurrency)
                .amount(request.getPriceLimit().getAmount())
                .build();

        User whoPosted = toUser(request.getWhoPosted());

        return Order.builder()
                .type(OrderType.BUY)
                .settlementState(OrderSettlementState.OPEN)
                .currencyPair(currencyPair)
                .requestedAmount(requestedAmount)
                .remainingAmount(requestedAmount)
                .priceLimit(priceLimit)
                .whoPosted(whoPosted)
                .build();
    }

    Order toOrder(SellOrderRequestTo request) {
        Currency firstCurrency = toCurrency(request.getCurrencyPair().getFirstCurrency().getCode());
        Currency secondCurrency = toCurrency(request.getCurrencyPair().getSecondCurrency().getCode());
        CurrencyPair currencyPair = toCurrencyPair(firstCurrency, secondCurrency);

        Money requestedAmount = Money.builder()
                .currency(secondCurrency)
                .amount(request.getRequestedAmount().getAmount())
                .build();

        Money priceLimit = Money.builder()
                .currency(firstCurrency)
                .amount(request.getPriceLimit().getAmount())
                .build();

        User whoPosted = toUser(request.getWhoPosted());

        return Order.builder()
                .type(OrderType.SELL)
                .settlementState(OrderSettlementState.OPEN)
                .currencyPair(currencyPair)
                .requestedAmount(requestedAmount)
                .remainingAmount(requestedAmount)
                .priceLimit(priceLimit)
                .whoPosted(whoPosted)
                .build();
    }

    private User toUser(UserId id) {
        return userRepository.getOne(id.getId());
    }
}
