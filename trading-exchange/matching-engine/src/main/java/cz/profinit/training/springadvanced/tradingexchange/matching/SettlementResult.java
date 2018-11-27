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

/**
 * Result of order settlement.
 */
@Value
@Builder
public class SettlementResult {

    /**
     * Settled order.
     */
    private final Order order;

    /**
     * Matched orders found and used during the settlement.
     */
    @Singular
    private final List<Order> matchedOrders;

    /**
     * Trades created from {@link #order order} and {@link #matchedOrders matched orders}.
     */
    @Singular
    private final List<Trade> trades;

    /**
     * User balance changes.
     * User balance increases when a sell order is settled and decreases when a buy order is settled.
     */
    @Getter(AccessLevel.NONE)
    private final UserBalanceChanges userBalanceChanges;

    /**
     * Returns all user balance changes.
     */
    public List<UserAndMoney> getAllUserBalanceChanges() {
        return userBalanceChanges.getAll();
    }

    /**
     * Used only in unit tests.
     */
    boolean hasUserBalanceChange(User user, Money money) {
        return userBalanceChanges.has(user, money);
    }

    /**
     * Used only in unit tests.
     */
    boolean hasUserBalanceChanges(User userA, Money moneyA,
                                  User userB, Money moneyB,
                                  User userC, Money moneyC,
                                  User userD, Money moneyD,
                                  User userE, Money moneyE,
                                  User userF, Money moneyF) {

        return hasUserBalanceChange(userA, moneyA)
                && hasUserBalanceChange(userB, moneyB)
                && hasUserBalanceChange(userC, moneyC)
                && hasUserBalanceChange(userD, moneyD)
                && hasUserBalanceChange(userE, moneyE)
                && hasUserBalanceChange(userF, moneyF);
    }
}
