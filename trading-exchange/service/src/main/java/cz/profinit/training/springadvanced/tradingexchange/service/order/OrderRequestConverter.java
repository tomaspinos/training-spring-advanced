package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.UserRepository;
import cz.profinit.training.springadvanced.tradingexchange.service.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
class OrderRequestConverter {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    Order toOrder(BuyOrderRequestTo request) {
        Currency requestedCurrency = currencyRepository.getOrCreate(request.getRequestedCurrency().getCode());
        Currency offeredCurrency = currencyRepository.getOrCreate(request.getOfferedCurrency().getCode());

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
                .requestedCurrency(requestedCurrency)
                .offeredCurrency(offeredCurrency)
                .orderAmount(orderAmount)
                .remainingAmount(orderAmount.copy())
                .priceLimit(priceLimit)
                .whoPosted(whoPosted)
                .whenCreated(LocalDateTime.now())
                .build();
    }

    Order toOrder(SellOrderRequestTo request) {
        Currency requestedCurrency = currencyRepository.getOrCreate(request.getRequestedCurrency().getCode());
        Currency offeredCurrency = currencyRepository.getOrCreate(request.getOfferedCurrency().getCode());

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
                .requestedCurrency(requestedCurrency)
                .offeredCurrency(offeredCurrency)
                .orderAmount(orderAmount)
                .remainingAmount(orderAmount.copy())
                .priceLimit(priceLimit)
                .whoPosted(whoPosted)
                .whenCreated(LocalDateTime.now())
                .build();
    }

    private User toUser(Username username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(User.class, username.getUsername()));
    }
}
