package cz.profinit.training.springadvanced.tradingexchange.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "t_money")
@Data
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
}
