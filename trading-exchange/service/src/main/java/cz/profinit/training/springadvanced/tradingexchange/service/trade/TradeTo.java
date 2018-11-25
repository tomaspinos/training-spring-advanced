package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import cz.profinit.training.springadvanced.tradingexchange.domain.TradeId;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import cz.profinit.training.springadvanced.tradingexchange.service.order.OrderStatusTo;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class TradeTo implements Serializable {

    private final TradeId id;
    private final OrderStatusTo buyOrder;
    private final OrderStatusTo sellOrder;
    private final MoneyTo amount;
    private final MoneyTo price;
    private final LocalDateTime whenCreated;

    public static TradeTo fromEntity(Trade trade) {
        return new TradeTo(
                trade.getId(),
                OrderStatusTo.fromEntity(trade.getBuyOrder()),
                OrderStatusTo.fromEntity(trade.getSellOrder()),
                MoneyTo.fromEntity(trade.getAmount()),
                MoneyTo.fromEntity(trade.getPrice()),
                trade.getWhenCreated());
    }
}
