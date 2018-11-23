package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.UserId;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OrderRequestConverter {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    private Currency toCurrency(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(Currency.class, code));
    }

    Order toOrder(BuyOrderRequestTo request) {
        Currency requestedCurrency = toCurrency(request.getRequestedCurrency().getCode());
        Currency offeredCurrency = toCurrency(request.getOfferedCurrency().getCode());

        Money orderAmount = Money.builder()
                .currency(requestedCurrency)
                .amount(request.getOrderAmount().getAmount())
                .build();

        Money priceLimit = Money.builder()
                .currency(offeredCurrency)
                .amount(request.getPriceLimit().getAmount())
                .build();

        User whoPosted = toUser(request.getWhoPosted());

        return Order.builder()
                .type(OrderType.BUY)
                .settlementState(OrderSettlementState.OPEN)
                .orderAmount(orderAmount)
                .remainingAmount(orderAmount)
                .priceLimit(priceLimit)
                .whoPosted(whoPosted)
                .build();
    }

    Order toOrder(SellOrderRequestTo request) {
        Currency requestedCurrency = toCurrency(request.getRequestedCurrency().getCode());
        Currency offeredCurrency = toCurrency(request.getOfferedCurrency().getCode());

        Money orderAmount = Money.builder()
                .currency(offeredCurrency)
                .amount(request.getOrderAmount().getAmount())
                .build();

        Money priceLimit = Money.builder()
                .currency(requestedCurrency)
                .amount(request.getPriceLimit().getAmount())
                .build();

        User whoPosted = toUser(request.getWhoPosted());

        return Order.builder()
                .type(OrderType.SELL)
                .settlementState(OrderSettlementState.OPEN)
                .orderAmount(orderAmount)
                .remainingAmount(orderAmount)
                .priceLimit(priceLimit)
                .whoPosted(whoPosted)
                .build();
    }

    private User toUser(UserId id) {
        return userRepository.getOne(id.getId());
    }
}
