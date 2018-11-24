package cz.profinit.training.springadvanced.tradingexchange.repository;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderSettlementState;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTypeAndSettlementStateInAndRequestedCurrencyAndOfferedCurrencyAndPriceLimitAmountGreaterThanEqual(
            OrderType type, List<OrderSettlementState> settlementStates,
            Currency requestedCurrency, Currency offeredCurrency, BigDecimal minPrice);

    List<Order> findByTypeAndSettlementStateInAndRequestedCurrencyAndOfferedCurrencyAndPriceLimitAmountLessThanEqual(
            OrderType type, List<OrderSettlementState> settlementStates,
            Currency requestedCurrency, Currency offeredCurrency, BigDecimal maxPrice);
}
