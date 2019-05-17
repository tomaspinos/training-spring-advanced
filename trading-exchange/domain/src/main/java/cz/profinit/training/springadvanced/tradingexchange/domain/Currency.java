package cz.profinit.training.springadvanced.tradingexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "c_currency")
@Cacheable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class Currency implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    /**
     * ISO 4217 currency code for this currency.
     */
    @Column(unique = true)
    @NotNull
    private String code;

    public CurrencyId getId() {
        return CurrencyId.of(id);
    }
}
