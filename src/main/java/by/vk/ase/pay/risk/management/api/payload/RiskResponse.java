package by.vk.ase.pay.risk.management.api.payload;

import jakarta.validation.constraints.NotNull;

public record RiskResponse(Boolean detected,
                           @NotNull(message = "The risk type should be specified") RiskType riskType) {

}
