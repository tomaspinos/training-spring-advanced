package cz.profinit.training.springadvanced.tradingexchange.repository;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCode(String code);

    default Currency getOrCreate(String code) {
        return findByCode(code)
                .orElseGet(() -> save(Currency.builder().code(code).build()));
    }
}
