package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class StreamingTradeServiceImpl implements StreamingTradeService {

    private final TradeService tradeService;

    @Override
    public Flux<TradeTo> getTrades() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(l -> LocalDateTime.now())
                .scan(Range.of(LocalDateTime.now(), LocalDateTime.now()), (range, date) -> Range.of(range.getTo(), date))
                .skip(1)
                .flatMap(range -> Flux.fromIterable(tradeService.getTrades(range.from, range.to)));
    }

    @Value(staticConstructor = "of")
    static class Range {

        private final LocalDateTime from;
        private final LocalDateTime to;
    }
}
