package cz.profinit.training.springadvanced.tradingexchange.service.user;

import cz.profinit.training.springadvanced.tradingexchange.domain.UserBalance;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class UserBalanceTo implements Serializable {

    private final MoneyTo total;

    public static UserBalanceTo fromEntity(UserBalance userBalance) {
        return new UserBalanceTo(MoneyTo.fromEntity(userBalance.getTotal()));
    }

    public static List<UserBalanceTo> fromEntities(List<UserBalance> userBalances) {
        return userBalances.stream().map(UserBalanceTo::fromEntity).collect(Collectors.toList());
    }
}
