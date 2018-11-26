package cz.profinit.training.springadvanced.tradingexchange.api;

import cz.profinit.training.springadvanced.tradingexchange.domain.TradeId;
import cz.profinit.training.springadvanced.tradingexchange.service.trade.StreamingTradeService;
import cz.profinit.training.springadvanced.tradingexchange.service.trade.TradeService;
import cz.profinit.training.springadvanced.tradingexchange.service.trade.TradeSummaryTo;
import cz.profinit.training.springadvanced.tradingexchange.service.trade.TradeTo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;
    private final StreamingTradeService streamingTradeService;

    @GetMapping("/{tradeId}")
    public ResponseEntity<TradeTo> getTrade(@PathVariable("tradeId") Long tradeId) {
        return tradeService.getTrade(TradeId.of(tradeId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stream")
    public Flux<TradeSummaryTo> trades() {
        return streamingTradeService.getTrades();
    }
}
