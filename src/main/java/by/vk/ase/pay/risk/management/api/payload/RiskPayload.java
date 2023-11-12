package by.vk.ase.pay.risk.management.api.payload;

import by.vk.ase.pay.common.Currency;
import by.vk.ase.pay.ledger.repository.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RiskPayload(UUID transactionId, Long senderId, Long recipientId, BigDecimal amount,
                          Currency source, Currency target, BigDecimal exchangeRate, Status status,
                          LocalDateTime creationDateTime) {

}
