package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "t_money")
@Data
@EqualsAndHashCode(of = "id")
@Builder
public class Money implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private Currency currency;

    @NotNull
    private BigDecimal amount;

    public static Money of(Currency currency, long amount) {
        return of(currency, new BigDecimal(amount));
    }

    public static Money of(Currency currency, BigDecimal amount) {
        return Money.builder()
                .currency(currency)
                .amount(amount)
                .build();
    }

    public boolean matches(Money money) {
        return Objects.equals(currency, money.currency) && Objects.equals(amount, money.amount);
    }
}
