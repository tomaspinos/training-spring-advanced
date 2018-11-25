package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "t_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderSettlementState settlementState;

    @ManyToOne
    @NotNull
    private Currency requestedCurrency;

    @ManyToOne
    @NotNull
    private Currency offeredCurrency;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money orderAmount;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money remainingAmount;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money priceLimit;

    @ManyToOne
    @NotNull
    private User whoPosted;

    @NotNull
    private LocalDateTime whenCreated;

    public OrderId getId() {
        return OrderId.of(id);
    }

    public static Order buy(long id, Currency requestedCurrency, Currency offeredCurrency, long requestedAmount, long maxPriceLimit, User whoPosted) {
        return buy(id, requestedCurrency, offeredCurrency, new BigDecimal(requestedAmount), new BigDecimal(maxPriceLimit), whoPosted);
    }

    public static Order buy(long id, Currency requestedCurrency, Currency offeredCurrency, BigDecimal requestedAmount, BigDecimal maxPriceLimit, User whoPosted) {
        Money orderAmount = Money.of(requestedCurrency, requestedAmount);
        return Order.builder()
                .id(id)
                .type(OrderType.BUY)
                .settlementState(OrderSettlementState.OPEN)
                .requestedCurrency(requestedCurrency)
                .offeredCurrency(offeredCurrency)
                .orderAmount(orderAmount)
                .remainingAmount(orderAmount)
                .priceLimit(Money.of(offeredCurrency, maxPriceLimit))
                .whoPosted(whoPosted)
                .whenCreated(LocalDateTime.now())
                .build();
    }

    public static Order sell(long id, Currency requestedCurrency, Currency offeredCurrency, long offeredAmount, long minPriceLimit, User whoPosted) {
        return sell(id, requestedCurrency, offeredCurrency, new BigDecimal(offeredAmount), new BigDecimal(minPriceLimit), whoPosted);
    }

    public static Order sell(long id, Currency requestedCurrency, Currency offeredCurrency, BigDecimal offeredAmount, BigDecimal minPriceLimit, User whoPosted) {
        Money orderAmount = Money.of(offeredCurrency, offeredAmount);
        return Order.builder()
                .id(id)
                .type(OrderType.SELL)
                .settlementState(OrderSettlementState.OPEN)
                .requestedCurrency(requestedCurrency)
                .offeredCurrency(offeredCurrency)
                .orderAmount(orderAmount)
                .remainingAmount(orderAmount)
                .priceLimit(Money.of(requestedCurrency, minPriceLimit))
                .whoPosted(whoPosted)
                .whenCreated(LocalDateTime.now())
                .build();
    }

    @Valid
    public boolean isValid() {
        return !Objects.equals(requestedCurrency, offeredCurrency)
                && Objects.equals(orderAmount.getCurrency(), remainingAmount.getCurrency())
                && validateMatchingTypeAndPriceLimit();
    }

    private boolean validateMatchingTypeAndPriceLimit() {
        switch (type) {
            case BUY:
                return Objects.equals(offeredCurrency, priceLimit.getCurrency());
            case SELL:
                return Objects.equals(requestedCurrency, priceLimit.getCurrency());
            default:
                throw new IllegalStateException("Unknown type: " + type);
        }
    }
}
