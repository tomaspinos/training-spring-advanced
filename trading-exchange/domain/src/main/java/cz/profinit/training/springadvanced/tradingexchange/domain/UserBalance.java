package cz.profinit.training.springadvanced.tradingexchange.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "t_user_balance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class UserBalance implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money total;

    public static UserBalance of(Money total) {
        return UserBalance.builder().total(total).build();
    }

    public static UserBalance zero(Currency currency) {
        return UserBalance.builder().total(Money.of(currency, BigDecimal.ZERO)).build();
    }

    public void add(BigDecimal amount) {
        total.setAmount(total.getAmount().add(amount));
    }

    public boolean isGreaterThanOrEqualTo(Money money) {
        return total.isGreaterThanOrEqualTo(money);
    }

    public boolean matches(Currency currency) {
        return Objects.equals(total.getCurrency(), currency);
    }
}
