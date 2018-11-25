package cz.profinit.training.springadvanced.tradingexchange.repository;

import cz.profinit.training.springadvanced.tradingexchange.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByWhenCreatedBetweenOrderByWhenCreatedAsc(LocalDateTime whenCreatedFrom, LocalDateTime whenCreatedTo);
}
