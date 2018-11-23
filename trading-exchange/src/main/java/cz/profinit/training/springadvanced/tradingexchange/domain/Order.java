package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
import java.util.Objects;

@Entity(name = "t_order")
@Data
@EqualsAndHashCode(of = "id")
@Builder
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

    public OrderId getId() {
        return OrderId.of(id);
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
