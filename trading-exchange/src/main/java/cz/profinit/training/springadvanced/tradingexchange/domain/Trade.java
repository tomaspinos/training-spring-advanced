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
import java.util.Objects;

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
