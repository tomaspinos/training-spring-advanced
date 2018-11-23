package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "c_currency_pair")
@Data
@EqualsAndHashCode(of = "id")
public class CurrencyPair implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private Currency firstCurrency;

    @ManyToOne
    @NotNull
    private Currency secondCurrency;

    public CurrencyPairId getId() {
        return CurrencyPairId.of(id);
    }
}
