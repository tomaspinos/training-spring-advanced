package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import cz.profinit.training.springadvanced.tradingexchange.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SettlementResult {

    private final Order order;

    @Singular
    private final List<Order> matchedOrders;

    @Singular
    private final List<Trade> trades;

    @Getter(AccessLevel.NONE)
    private final UserBalanceChanges userBalanceChanges;

    public List<UserAndMoney> getAllUserBalanceChanges() {
        return userBalanceChanges.getAll();
    }

    public List<Money> getUserBalanceChanges(User user) {
        return userBalanceChanges.get(user);
    }

    boolean hasUserBalanceChange(User user, Money money) {
        return userBalanceChanges.has(user, money);
    }
}
