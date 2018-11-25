package cz.profinit.training.springadvanced.tradingexchange.api;

import cz.profinit.training.springadvanced.tradingexchange.service.trade.StreamingTradeService;
import cz.profinit.training.springadvanced.tradingexchange.service.trade.TradeTo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final StreamingTradeService streamingTradeService;

    @GetMapping
    public Flux<TradeTo> trades() {
        return streamingTradeService.getTrades();
    }
}
