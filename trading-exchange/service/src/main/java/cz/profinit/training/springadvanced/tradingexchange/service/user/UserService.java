package cz.profinit.training.springadvanced.tradingexchange.service.user;

import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;

import java.util.Optional;

public interface UserService {

    UserTo createUser(Username username);

    Optional<UserTo> deposit(Username username, MoneyTo money);

    Optional<UserTo> getUser(Username username);
}
