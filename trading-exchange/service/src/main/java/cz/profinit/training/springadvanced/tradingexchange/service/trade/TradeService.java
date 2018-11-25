package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import java.time.LocalDateTime;
import java.util.List;

public interface TradeService {

    List<TradeTo> getTrades(LocalDateTime from, LocalDateTime to);
}
