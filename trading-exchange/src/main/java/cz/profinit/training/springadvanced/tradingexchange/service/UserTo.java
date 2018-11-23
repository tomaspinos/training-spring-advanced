package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.UserId;
import lombok.Value;

import java.io.Serializable;

@Value
public class UserTo implements Serializable {

    private final UserId id;
    private final String username;

    public static UserTo fromEntity(User user) {
        return new UserTo(user.getId(), user.getUsername());
    }
}
