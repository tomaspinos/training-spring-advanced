package cz.profinit.training.springadvanced.tradingexchange.service.trade;

import cz.profinit.training.springadvanced.tradingexchange.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TradeTo> getTrades(LocalDateTime from, LocalDateTime to) {
        return tradeRepository.findByWhenCreatedBetweenOrderByWhenCreatedAsc(from, to).stream()
                .map(TradeTo::fromEntity)
                .collect(Collectors.toList());
    }
}
