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
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    private CurrencyPair currencyPair;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money requestedAmount;

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
}
