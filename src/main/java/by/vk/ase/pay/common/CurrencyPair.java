package by.vk.ase.pay.common;

/**
 * The currency pair for exchange rates monitoring.
 *
 * @param source the currency sourceCurrency
 * @param target the currency targetCurrency
 */
public record CurrencyPair(Currency source, Currency target) {

}
