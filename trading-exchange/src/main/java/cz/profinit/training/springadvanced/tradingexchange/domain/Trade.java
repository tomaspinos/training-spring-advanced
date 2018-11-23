package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "t_trade")
@Data
@EqualsAndHashCode(of = "id")
@Builder
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
}
