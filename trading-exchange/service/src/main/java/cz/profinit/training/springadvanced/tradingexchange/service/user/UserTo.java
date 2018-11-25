package cz.profinit.training.springadvanced.tradingexchange.service.user;

import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.UserId;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class UserTo implements Serializable {

    private final UserId id;
    private final Username username;
    private final List<UserBalanceTo> balances;

    public static UserTo fromEntity(User user) {
        return new UserTo(user.getId(), user.getUsername(), UserBalanceTo.fromEntities(user.getBalances()));
    }
}
