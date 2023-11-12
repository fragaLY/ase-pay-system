package by.vk.ase.pay.wallet.payload;

import by.vk.ase.pay.common.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record WalletResponse(@NotNull(message = "Sender's id should not be null") Long senderId,
                             @NotNull(message = "Recipient's id should not be null") Long recipientId,
                             @Positive(message = "The transferring amount should be greater for 0") BigDecimal amountInSourceCurrency,
                             @Positive(message = "The receiving amount should be greater for 0") BigDecimal amountInTargetCurrency,
                             @NotNull(message = "The sourceCurrency currency should not be null") Currency sourceCurrency,
                             @NotNull(message = "The targetCurrency currency should not be null") Currency targetCurrency,
                             @NotNull(message = "The status of transferring should not be null") Status status,
                             String reason) {

}
