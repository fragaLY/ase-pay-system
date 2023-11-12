package by.vk.ase.pay.risk.management.service;

import by.vk.ase.pay.ledger.service.RiskPayload;
import by.vk.ase.pay.risk.management.api.payload.RiskResponse;
import by.vk.ase.pay.risk.management.api.payload.RiskType;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This service emulates the risk management service processes.
 */
@Slf4j
@Service
public record RiskManagementService() {

  /**
   * The "mock" of analyzing processes. Returns a risky transaction in ratio of 1 : 10.
   *
   * @param payload the payload to be analyzed
   * @return risk management system result response
   */
  @NotNull
  public RiskResponse analyze(RiskPayload payload) {
    log.info("[RISK MANAGEMENT] Starting analyzing transaction = [{}]", payload);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    var isRisky = ThreadLocalRandom.current().nextInt(11) == 0;
    RiskResponse response;
    if (isRisky) {
      var type = Arrays.stream(RiskType.values())
          .filter(it -> !it.equals(RiskType.NONE))
          .findAny()
          .orElse(RiskType.UNDEFINED);
      response = new RiskResponse(Boolean.TRUE, type);
      log.warn("[RISK MANAGEMENT] The transaction = [{}] has risk = [{}]", payload, response);
    } else {
      response = new RiskResponse(Boolean.FALSE, RiskType.NONE);
      log.info("[RISK MANAGEMENT] The transaction = [{}] has no risks", payload);
    }
    return response;
  }

}


