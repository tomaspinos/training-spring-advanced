package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "t_user_balance")
@Data
@EqualsAndHashCode(of = "id")
public class UserBalance implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @NotNull
    private Money total;

    public boolean matches(Currency currency) {
        return Objects.equals(total.getCurrency(), currency);
    }
}
