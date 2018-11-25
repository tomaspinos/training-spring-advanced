package cz.profinit.training.springadvanced.tradingexchange.service.user;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.UserRepository;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    @Override
    public UserTo createUser(Username username) {
        log.info("Creating user: {}", username);

        User user = userRepository.save(User.builder().username(username).build());
        return UserTo.fromEntity(user);
    }

    @Transactional
    @Override
    public Optional<UserTo> deposit(Username username, MoneyTo money) {
        log.info("Making deposit. User: {}, money: {}", username, money);

        Currency currency = currencyRepository.getOrCreate(money.getCurrency().getCode());

        return userRepository.findByUsername(username)
                .map(user -> {
                    user.modifyBalance(Money.of(currency, money.getAmount()));
                    return UserTo.fromEntity(user);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserTo> getUser(Username username) {
        log.info("Getting user: {}", username);

        return userRepository.findByUsername(username).map(UserTo::fromEntity);
    }
}
