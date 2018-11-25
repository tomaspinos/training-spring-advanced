package cz.profinit.training.springadvanced.tradingexchange.api;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.CurrencyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.order.BuyOrderRequestTo;
import cz.profinit.training.springadvanced.tradingexchange.service.order.OrderService;
import cz.profinit.training.springadvanced.tradingexchange.service.order.OrderStatusTo;
import cz.profinit.training.springadvanced.tradingexchange.service.order.SellOrderRequestTo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/buy/{whoPosted}/{requestedCurrencyCode}/{offeredCurrencyCode}/{amount}/{maxPrice}")
    public OrderStatusTo buy(@PathVariable("whoPosted") String whoPosted,
                             @PathVariable("requestedCurrencyCode") String requestedCurrencyCode,
                             @PathVariable("offeredCurrencyCode") String offeredCurrencyCode,
                             @PathVariable("amount") BigDecimal amount,
                             @PathVariable("maxPrice") BigDecimal maxPrice) {

        return orderService.createBuyOrder(
                BuyOrderRequestTo.of(CurrencyTo.of(requestedCurrencyCode), CurrencyTo.of(offeredCurrencyCode),
                        amount, maxPrice, Username.of(whoPosted)));
    }

    @PostMapping("/buy")
    public OrderStatusTo buy(@RequestBody BuyOrderRequestTo request) {
        return orderService.createBuyOrder(request);
    }

    @PostMapping("/sell/{whoPosted}/{requestedCurrencyCode}/{offeredCurrencyCode}/{amount}/{minPrice}")
    public OrderStatusTo sell(@PathVariable("whoPosted") String whoPosted,
                              @PathVariable("requestedCurrencyCode") String requestedCurrencyCode,
                              @PathVariable("offeredCurrencyCode") String offeredCurrencyCode,
                              @PathVariable("amount") BigDecimal amount,
                              @PathVariable("minPrice") BigDecimal minPrice) {

        return orderService.createSellOrder(
                SellOrderRequestTo.of(CurrencyTo.of(requestedCurrencyCode), CurrencyTo.of(offeredCurrencyCode),
                        amount, minPrice, Username.of(whoPosted)));
    }

    @PostMapping("/sell")
    public OrderStatusTo sell(@RequestBody SellOrderRequestTo request) {
        return orderService.createSellOrder(request);
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<OrderStatusTo> status(@PathVariable("orderId") long orderId) {
        return orderService.getOrderStatus(OrderId.of(orderId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
