package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "t_user")
@Data
@EqualsAndHashCode(of = "id")
@Builder
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String username;

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserBalance> balances = new ArrayList<>();

    public void addBalance(UserBalance balance) {
        balances.add(balance);
    }

    public Optional<UserBalance> getBalance(Currency currency) {
        return balances.stream().filter(balance -> balance.matches(currency)).findFirst();
    }

    public UserId getId() {
        return UserId.of(id);
    }
}
