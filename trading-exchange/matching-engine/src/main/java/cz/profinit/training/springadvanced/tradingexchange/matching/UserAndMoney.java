package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class UserAndMoney implements Serializable {

    private final User user;
    private final Money money;
}
