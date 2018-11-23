package cz.profinit.training.springadvanced.tradingexchange.api;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.service.BuyOrderRequestTo;
import cz.profinit.training.springadvanced.tradingexchange.service.OrderService;
import cz.profinit.training.springadvanced.tradingexchange.service.OrderStatusTo;
import cz.profinit.training.springadvanced.tradingexchange.service.SellOrderRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/buy")
    public OrderStatusTo buy(@RequestBody BuyOrderRequestTo request) {
        return orderService.createBuyOrder(request);
    }

    @PostMapping("/sell")
    public OrderStatusTo sell(@RequestBody SellOrderRequestTo request) {
        return orderService.createSellOrder(request);
    }

    @GetMapping("/status/{orderId}")
    public OrderStatusTo status(@PathVariable("orderId") long orderId) {
        return orderService.getOrderStatus(OrderId.of(orderId));
    }
}
