package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
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

    public boolean isGreaterThanZero() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isZero() {
        return amount.equals(BigDecimal.ZERO);
    }

    public Money min(Money other) {
        Assert.isTrue(Objects.equals(currency, other.currency), "Cannot compare different currencies");
        return amount.compareTo(other.amount) <= 0 ?
                Money.of(currency, amount) :
                Money.of(currency, other.amount);
    }

    /**
     * this - other.
     */
    public void minus(Money other) {
        Assert.isTrue(Objects.equals(currency, other.currency), "Cannot calculate with different currencies");
        amount = amount.subtract(other.amount);
    }
}
