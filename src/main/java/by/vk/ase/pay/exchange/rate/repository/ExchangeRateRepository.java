package by.vk.ase.pay.exchange.rate.repository;

import static by.vk.ase.pay.common.Currency.ARS;
import static by.vk.ase.pay.common.Currency.BRL;
import static by.vk.ase.pay.common.Currency.EUR;
import static by.vk.ase.pay.common.Currency.USD;
import static by.vk.ase.pay.common.Currency.UYU;

import by.vk.ase.pay.common.CurrencyPair;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

/**
 * The "mock" of exchange rate repository.
 */
@Repository
public record ExchangeRateRepository() {

  private static final ConcurrentHashMap<CurrencyPair, BigDecimal> EXCHANGE_RATES;

  static {
    EXCHANGE_RATES = new ConcurrentHashMap<>();

    EXCHANGE_RATES.put(new CurrencyPair(USD, USD), BigDecimal.ONE);
    EXCHANGE_RATES.put(new CurrencyPair(USD, EUR), new BigDecimal("0.8469"));
    EXCHANGE_RATES.put(new CurrencyPair(USD, BRL), new BigDecimal("5.3715"));
    EXCHANGE_RATES.put(new CurrencyPair(USD, ARS), new BigDecimal("181.7810"));
    EXCHANGE_RATES.put(new CurrencyPair(USD, UYU), new BigDecimal("42.4228"));

    EXCHANGE_RATES.put(new CurrencyPair(EUR, EUR), BigDecimal.ONE);
    EXCHANGE_RATES.put(new CurrencyPair(EUR, USD), new BigDecimal("1.1806"));
    EXCHANGE_RATES.put(new CurrencyPair(EUR, BRL), new BigDecimal("6.3403"));
    EXCHANGE_RATES.put(new CurrencyPair(EUR, ARS), new BigDecimal("214.5463"));
    EXCHANGE_RATES.put(new CurrencyPair(EUR, UYU), new BigDecimal("50.0810"));

    EXCHANGE_RATES.put(new CurrencyPair(BRL, BRL), BigDecimal.ONE);
    EXCHANGE_RATES.put(new CurrencyPair(BRL, USD), new BigDecimal("0.1862"));
    EXCHANGE_RATES.put(new CurrencyPair(BRL, EUR), new BigDecimal("0.1576"));
    EXCHANGE_RATES.put(new CurrencyPair(BRL, ARS), new BigDecimal("33.8481"));
    EXCHANGE_RATES.put(new CurrencyPair(BRL, UYU), new BigDecimal("7.8895"));

    EXCHANGE_RATES.put(new CurrencyPair(ARS, ARS), BigDecimal.ONE);
    EXCHANGE_RATES.put(new CurrencyPair(ARS, USD), new BigDecimal("0.0055"));
    EXCHANGE_RATES.put(new CurrencyPair(ARS, EUR), new BigDecimal("0.0047"));
    EXCHANGE_RATES.put(new CurrencyPair(ARS, BRL), new BigDecimal("0.0295"));
    EXCHANGE_RATES.put(new CurrencyPair(ARS, UYU), new BigDecimal("0.2330"));

    EXCHANGE_RATES.put(new CurrencyPair(UYU, UYU), BigDecimal.ONE);
    EXCHANGE_RATES.put(new CurrencyPair(UYU, USD), new BigDecimal("0.0236"));
    EXCHANGE_RATES.put(new CurrencyPair(UYU, EUR), new BigDecimal("0.0200"));
    EXCHANGE_RATES.put(new CurrencyPair(UYU, BRL), new BigDecimal("0.1267"));
    EXCHANGE_RATES.put(new CurrencyPair(UYU, ARS), new BigDecimal("4.2937"));
  }

  @Positive(message = "The exchange rate between currencies should be greater for zero")
  public BigDecimal exchangeRate(
      @NotNull(message = "The currency pair for exchange rate should not be null") CurrencyPair pair) {
    return EXCHANGE_RATES.get(pair);
  }

}

