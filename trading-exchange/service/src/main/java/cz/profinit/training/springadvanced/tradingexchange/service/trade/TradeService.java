package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import cz.profinit.training.springadvanced.tradingexchange.domain.TradeId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TradeService {

    Optional<TradeTo> getTrade(TradeId id);

    List<TradeSummaryTo> getTrades(LocalDateTime from, LocalDateTime to);
}
