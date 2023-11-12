package by.vk.ase.pay.exchange.rate.service;

import by.vk.ase.pay.common.CurrencyPair;
import by.vk.ase.pay.exchange.rate.payload.ExchangeRatePayload;
import by.vk.ase.pay.exchange.rate.repository.ExchangeRateRepository;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class emulates the work of exchange rate service with pre-calculated latency.
 *
 * @param repository the "mock" of exchange rate repository
 */
@Slf4j
@Service
public record ExchangeRateService(ExchangeRateRepository repository) {

  /**
   * Returns the latest result of exchange rates between currency that are streaming in a
   * real-time.
   *
   * @param payload the exchange rate payload
   * @return the exchange rate between sourceCurrency and targetCurrency currency
   */
  public BigDecimal exchangeRate(ExchangeRatePayload payload) {
    var pair = new CurrencyPair(payload.source(), payload.target());
    log.info(
        "[EXCHANGE RATE SERVICE] Requested exchange rate for transaction = [{}] between currencies = [{}]",
        payload.transactionId(), pair);
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    var exchangeRate = repository.exchangeRate(pair);
    log.info(
        "[EXCHANGE RATE SERVICE] Exchange rate for transaction = [{}] between currencies = [{}] is [{}]",
        payload.transactionId(), pair,
        exchangeRate);
    return exchangeRate;
  }
}
