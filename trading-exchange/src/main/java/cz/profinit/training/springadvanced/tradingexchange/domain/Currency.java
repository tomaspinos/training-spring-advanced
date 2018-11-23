package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "c_currency")
@Data
@EqualsAndHashCode(of = "id")
@Builder
public class Currency implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * ISO 4217 currency code for this currency.
     */
    @NotNull
    private String code;

    public CurrencyId getId() {
        return CurrencyId.of(id);
    }
}
