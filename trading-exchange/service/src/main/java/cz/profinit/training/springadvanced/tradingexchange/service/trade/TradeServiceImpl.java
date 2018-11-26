package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import cz.profinit.training.springadvanced.tradingexchange.domain.TradeId;
import cz.profinit.training.springadvanced.tradingexchange.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public Optional<TradeTo> getTrade(TradeId id) {
        return tradeRepository.findById(id.getId()).map(TradeTo::fromEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TradeSummaryTo> getTrades(LocalDateTime from, LocalDateTime to) {
        return tradeRepository.findByWhenCreatedBetweenOrderByWhenCreatedAsc(from, to).stream()
                .map(TradeSummaryTo::fromEntity)
                .collect(Collectors.toList());
    }
}
