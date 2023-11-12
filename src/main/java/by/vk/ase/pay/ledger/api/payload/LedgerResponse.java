package by.vk.ase.pay.ledger.api.payload;

import by.vk.ase.pay.ledger.repository.Status;
import jakarta.validation.constraints.NotNull;

public record LedgerResponse(
    @NotNull(message = "The status of transaction processing should not be null") Status status,
    String reason) {

}
