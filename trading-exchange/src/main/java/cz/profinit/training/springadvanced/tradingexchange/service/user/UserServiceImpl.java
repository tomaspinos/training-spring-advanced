package cz.profinit.training.springadvanced.tradingexchange.service.user;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import cz.profinit.training.springadvanced.tradingexchange.domain.UserBalance;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.repository.CurrencyRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.UserRepository;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    @Override
    public UserTo createUser(Username username) {
        User user = userRepository.save(User.builder().username(username).build());
        return UserTo.fromEntity(user);
    }

    @Transactional
    @Override
    public Optional<UserTo> deposit(Username username, MoneyTo money) {
        Currency currency = getOrCreateCurrency(money.getCurrency().getCode());

        return userRepository.findByUsername(username)
                .map(user -> {
                    Optional<UserBalance> maybeBalance = user.getBalance(currency);

                    if (maybeBalance.isPresent()) {
                        UserBalance balance = maybeBalance.get();
                        balance.modify(money.getAmount());
                    } else {
                        user.addBalance(UserBalance.of(Money.of(currency, money.getAmount())));
                    }

                    return UserTo.fromEntity(user);
                });
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserTo> getUser(Username username) {
        return userRepository.findByUsername(username).map(UserTo::fromEntity);
    }

    private Currency getOrCreateCurrency(String code) {
        return currencyRepository.findByCode(code)
                .orElseGet(() -> currencyRepository.save(Currency.builder().code(code).build()));
    }
}
