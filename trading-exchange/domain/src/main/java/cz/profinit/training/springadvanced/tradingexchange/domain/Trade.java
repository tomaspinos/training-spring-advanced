package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Trade means money was exchanged.
 * Trade is a result of order settlement.
 */
@Entity(name = "t_trade")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Trade implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private Order buyOrder;

    @ManyToOne
    @NotNull
    private Order sellOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money price;

    @NotNull
    private LocalDateTime whenCreated;

    public TradeId getId() {
        return TradeId.of(id);
    }

    public boolean matches(Order buyOrder, Order sellOrder, Money amount, Money price) {
        return Objects.equals(this.buyOrder, buyOrder)
                && Objects.equals(this.sellOrder, sellOrder)
                && this.amount.matches(amount)
                && this.price.matches(price);
    }
}
