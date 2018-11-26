package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import cz.profinit.training.springadvanced.tradingexchange.domain.OrderId;
import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import cz.profinit.training.springadvanced.tradingexchange.domain.TradeId;
import cz.profinit.training.springadvanced.tradingexchange.domain.Username;
import cz.profinit.training.springadvanced.tradingexchange.service.MoneyTo;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class TradeSummaryTo implements Serializable {

    private final TradeId id;
    private final OrderId buyOrderId;
    private final Username whoPostedBuyOrder;
    private final OrderId sellOrderId;
    private final Username whoPostedSellOrder;
    private final MoneyTo amount;
    private final MoneyTo price;
    private final LocalDateTime whenCreated;

    public static TradeSummaryTo fromEntity(Trade trade) {
        return new TradeSummaryTo(
                trade.getId(),
                trade.getBuyOrder().getId(),
                trade.getBuyOrder().getWhoPosted().getUsername(),
                trade.getSellOrder().getId(),
                trade.getSellOrder().getWhoPosted().getUsername(),
                MoneyTo.fromEntity(trade.getAmount()),
                MoneyTo.fromEntity(trade.getPrice()),
                trade.getWhenCreated());
    }
}
