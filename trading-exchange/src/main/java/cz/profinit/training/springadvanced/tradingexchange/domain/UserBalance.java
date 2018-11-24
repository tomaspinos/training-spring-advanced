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
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money total;

    public static UserBalance of(Money total) {
        return UserBalance.builder().total(total).build();
    }

    public void modify(BigDecimal amount) {
        total.setAmount(total.getAmount().add(amount));
    }

    public boolean matches(Currency currency) {
        return Objects.equals(total.getCurrency(), currency);
    }
}
