package cz.profinit.training.springadvanced.tradingexchange.api;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/buy")
    public void buy() {
        orderService.createBuyOrder();
    }

    @GetMapping("/status/{orderId}")
    public void order(@PathVariable("orderId") long orderId) {
        orderService.getOrder(OrderId.of(orderId));
    }

    @PostMapping("/sell")
    public void sell() {
        orderService.createSellOrder();
    }
}
