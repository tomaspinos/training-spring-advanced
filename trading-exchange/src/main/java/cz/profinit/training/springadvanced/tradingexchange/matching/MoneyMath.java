package cz.profinit.training.springadvanced.tradingexchange.matching;

import cz.profinit.training.springadvanced.tradingexchange.domain.Money;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class MoneyMath {

    static boolean isZero(Money money) {
        return money.getAmount().equals(BigDecimal.ZERO);
    }

    static boolean isGreaterThanZero(Money money) {
        return money.getAmount().compareTo(BigDecimal.ZERO) > 0;
    }

    static Money min(Money a, Money b) {
        Assert.isTrue(Objects.equals(a.getCurrency(), b.getCurrency()), "Cannot compare different currencies");
        return a.getAmount().compareTo(b.getAmount()) <= 0 ? a : b;
    }

    /**
     * a - b.
     */
    static void minus(Money a, Money b) {
        Assert.isTrue(Objects.equals(a.getCurrency(), b.getCurrency()), "Cannot calculate with different currencies");
        a.setAmount(a.getAmount().subtract(b.getAmount()));
    }
}
