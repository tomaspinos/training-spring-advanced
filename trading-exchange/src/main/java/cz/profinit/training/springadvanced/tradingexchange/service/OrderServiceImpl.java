package cz.profinit.training.springadvanced.tradingexchange.service;

import cz.profinit.training.springadvanced.tradingexchange.domain.Order;
import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {

    private final OrderRequestConverter orderRequestConverter;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderStatusTo createBuyOrder(BuyOrderRequestTo request) {
        Order order = orderRequestConverter.toOrder(request);
        Order savedOrder = orderRepository.save(order);
        return OrderStatusTo.fromEntity(savedOrder);
    }

    @Transactional
    @Override
    public OrderStatusTo createSellOrder(SellOrderRequestTo request) {
        Order order = orderRequestConverter.toOrder(request);
        Order savedOrder = orderRepository.save(order);
        return OrderStatusTo.fromEntity(savedOrder);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderStatusTo getOrderStatus(OrderId id) {
        Order order = orderRepository.getOne(id.getId());
        return OrderStatusTo.fromEntity(order);
    }
}
