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

    public Money copy() {
        return of(currency, amount);
    }

    public Money getNegative() {
        return of(currency, amount.negate());
    }

    public boolean isGreaterThanZero() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterOrEqualToZero() {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isLessThan(Money other) {
        Assert.isTrue(Objects.equals(currency, other.currency), "Same currency expected");
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean matches(Money money) {
        return Objects.equals(currency, money.currency) && Objects.equals(amount, money.amount);
    }

    public Money min(Money other) {
        Assert.isTrue(Objects.equals(currency, other.currency), "Same currency expected");
        return amount.compareTo(other.amount) <= 0 ?
                Money.of(currency, amount) :
                Money.of(currency, other.amount);
    }

    /**
     * this - other.
     */
    public void minus(Money other) {
        Assert.isTrue(Objects.equals(currency, other.currency), "Same currency expected");
        amount = amount.subtract(other.amount);
    }

    /**
     * this + other
     */
    public void plus(Money other) {
        Assert.isTrue(Objects.equals(currency, other.currency), "Same currency expected");
        amount = amount.add(other.amount);
    }
}
