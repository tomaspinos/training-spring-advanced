package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import reactor.core.publisher.Flux;

public interface StreamingTradeService {

    Flux<TradeTo> getTrades();
}
