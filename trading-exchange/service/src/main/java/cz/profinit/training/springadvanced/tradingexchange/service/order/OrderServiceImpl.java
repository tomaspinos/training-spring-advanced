package cz.profinit.training.springadvanced.tradingexchange.service.order;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.matching.MatchingEngine;
import cz.profinit.training.springadvanced.tradingexchange.matching.SettlementResult;
import cz.profinit.training.springadvanced.tradingexchange.repository.OrderRepository;
import cz.profinit.training.springadvanced.tradingexchange.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class OrderServiceImpl implements OrderService {

    private final MatchingEngine matchingEngine;
    private final OrderRequestConverter orderRequestConverter;
    private final OrderRepository orderRepository;
    private final TradeRepository tradeRepository;

    @Transactional
    @Override
    public OrderStatusTo createBuyOrder(BuyOrderRequestTo request) {
        log.info("Creating buy order: {}", request);

        synchronized (this) {
            Order order = orderRepository.save(orderRequestConverter.toOrder(request));
            SettlementResult settlementResult = matchingEngine.settleBuyOrder(order);
            settlementResult.getTrades().forEach(tradeRepository::save);
            settlementResult.getAllUserBalanceChanges()
                    .forEach(userAndMoney -> userAndMoney.getUser().modifyBalance(userAndMoney.getMoney()));
            return OrderStatusTo.fromEntity(order);
        }
    }

    @Transactional
    @Override
    public OrderStatusTo createSellOrder(SellOrderRequestTo request) {
        log.info("Creating sell order: {}", request);

        synchronized (this) {
            Order order = orderRepository.save(orderRequestConverter.toOrder(request));
            SettlementResult settlementResult = matchingEngine.settleSellOrder(order);
            settlementResult.getTrades().forEach(tradeRepository::save);
            settlementResult.getAllUserBalanceChanges()
                    .forEach(userAndMoney -> userAndMoney.getUser().modifyBalance(userAndMoney.getMoney()));
            return OrderStatusTo.fromEntity(order);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<OrderStatusTo> getOrderStatus(OrderId id) {
        log.info("Getting order status: {}", id);

        return orderRepository.findById(id.getId()).map(OrderStatusTo::fromEntity);
    }
}
