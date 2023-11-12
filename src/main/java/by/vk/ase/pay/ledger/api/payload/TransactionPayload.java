package by.vk.ase.pay.ledger.api.payload;

import by.vk.ase.pay.common.Currency;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionPayload(
    @NotNull(message = "The sender id should not be null") Long senderId,
    @NotNull(message = "The recipient id should not be null") Long recipientId,
    @DecimalMin(value = "10", message = "The transferring amount should be at least of 10")
    @DecimalMax(value = "10000", message = "The transferring amount should not be greater for 10000")
    @Digits(integer = 5, fraction = 2)
    BigDecimal amount,
    @NotNull Currency sourceCurrency,
    @NotNull Currency targetCurrency) {

}
