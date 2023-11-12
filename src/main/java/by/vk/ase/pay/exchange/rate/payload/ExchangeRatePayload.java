package by.vk.ase.pay.exchange.rate.payload;

import by.vk.ase.pay.common.Currency;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ExchangeRatePayload(
    @NotNull(message = "The transaction id should not be null") UUID transactionId,
    @NotNull(message = "The currency sourceCurrency should not be null") Currency source,
    @NotNull(message = "The currency targetCurrency should not be null") Currency target) {

}
