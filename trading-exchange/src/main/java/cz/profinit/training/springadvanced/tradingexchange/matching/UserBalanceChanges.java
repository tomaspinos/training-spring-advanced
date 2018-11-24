package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import lombok.Value;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Value
class UserBalanceChanges implements Serializable {

    private final Map<UserAndCurrency, Money> changes = new HashMap<>();

    void add(User user, Money money) {
        UserAndCurrency userAndCurrency = UserAndCurrency.of(user, money.getCurrency());
        Money currentMoney = changes.get(userAndCurrency);
        if (currentMoney == null) {
            changes.put(userAndCurrency, money.copy());
        } else {
            currentMoney.plus(money);
        }
    }

    List<Money> get(User user) {
        return changes.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getKey().user, user))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    List<UserAndMoney> getAll() {
        return changes.entrySet().stream()
                .map(entry -> UserAndMoney.of(entry.getKey().user, entry.getValue()))
                .collect(Collectors.toList());
    }

    boolean has(User user, Money money) {
        return Objects.equals(changes.get(UserAndCurrency.of(user, money.getCurrency())), money);
    }

    @Value(staticConstructor = "of")
    private static class UserAndCurrency implements Serializable {

        private final User user;
        private final Currency currency;
    }
}
