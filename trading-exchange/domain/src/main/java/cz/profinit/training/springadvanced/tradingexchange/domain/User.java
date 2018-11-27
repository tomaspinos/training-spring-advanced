package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Username username;

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserBalance> balances = new ArrayList<>();

    public Optional<UserBalance> getBalance(Currency currency) {
        return balances.stream().filter(balance -> balance.matches(currency)).findFirst();
    }

    public UserId getId() {
        return UserId.of(id);
    }

    public void modifyBalance(Money money) {
        Optional<UserBalance> maybeBalance = getBalance(money.getCurrency());

        if (maybeBalance.isPresent()) {
            maybeBalance.get().add(money.getAmount());
        } else {
            Assert.isTrue(money.isGreaterThanOrEqualToZero(), "Invalid balance: " + money);
            balances.add(UserBalance.of(money));
        }
    }
}
