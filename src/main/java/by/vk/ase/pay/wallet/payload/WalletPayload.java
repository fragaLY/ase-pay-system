package by.vk.ase.pay.wallet.payload;

import by.vk.ase.pay.common.Currency;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record WalletPayload(
    @NotNull(message = "The transaction id should not be null") UUID transactionId,
    @NotNull(message = "Sender's id should not be null") Long senderId,
    @NotNull(message = "Recipient's id should not be null") Long recipientId,
    @DecimalMin(value = "10", message = "The transferring amount should be at least of 10")
    @DecimalMax(value = "10000", message = "The transferring amount should not be greater for 10000")
    @Digits(integer = 5, fraction = 2)
    BigDecimal amount,
    @NotNull(message = "Source currency should not be null") Currency sourceCurrency,
    @NotNull(message = "Target currency should not be null") Currency targetCurrency,
    @Positive(message = "The exchange rate should be greater for 0") BigDecimal exchangeRate,
    LocalDateTime creationTime) {

}
