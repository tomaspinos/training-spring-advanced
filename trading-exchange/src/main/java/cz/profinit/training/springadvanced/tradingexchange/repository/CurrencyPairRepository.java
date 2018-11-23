package cz.profinit.training.springadvanced.tradingexchange.repository;

import cz.profinit.training.springadvanced.tradingexchange.domain.Currency;
import cz.profinit.training.springadvanced.tradingexchange.domain.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {

    Optional<CurrencyPair> findByFirstCurrencyAndSecondCurrency(Currency firstCurrency, Currency secondCurrency);
}
