package by.vk.ase.pay.ledger.service;

import by.vk.ase.pay.exchange.rate.payload.ExchangeRatePayload;
import by.vk.ase.pay.exchange.rate.service.ExchangeRateService;
import by.vk.ase.pay.ledger.api.payload.LedgerResponse;
import by.vk.ase.pay.ledger.api.payload.TransactionPayload;
import by.vk.ase.pay.ledger.repository.Status;
import by.vk.ase.pay.ledger.repository.Transaction;
import by.vk.ase.pay.ledger.repository.TransactionRepository;
import by.vk.ase.pay.risk.management.service.RiskManagementService;
import by.vk.ase.pay.wallet.payload.WalletPayload;
import by.vk.ase.pay.wallet.service.WalletService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LedgerService {

  private final TransactionRepository transactionRepository;
  private final ExchangeRateService exchangeRateService;
  private final RiskManagementService riskManagementService;
  private final WalletService walletService;

  @Transactional(propagation = Propagation.REQUIRED)
  public LedgerResponse transfer(TransactionPayload payload) {
    var transactionId = UUID.randomUUID();
    log.info("[LEDGER SERVICE] Requested transaction = [{}] with id = [{}]", payload,
        transactionId);
    var exchangeRate = exchangeRateService.exchangeRate(
        new ExchangeRatePayload(transactionId, payload.sourceCurrency(), payload.targetCurrency()));
    var transaction = new Transaction(transactionId, payload.senderId(), payload.recipientId(),
        payload.amount(),
        payload.sourceCurrency(), payload.targetCurrency(), exchangeRate, Status.REQUESTED,
        LocalDateTime.now(),
        LocalDateTime.now());
    transactionRepository.save(transaction);
    var risk = riskManagementService.analyze(
        new RiskPayload(transactionId, payload.senderId(), payload.recipientId(),
            payload.amount(),
            payload.sourceCurrency(), payload.targetCurrency(), exchangeRate, Status.REQUESTED,
            LocalDateTime.now()));
    LedgerResponse response;
    if (!risk.detected()) {
      var walletResponse = walletService.transfer(
          new WalletPayload(transactionId, payload.senderId(), payload.recipientId(),
              payload.amount(),
              payload.sourceCurrency(), payload.targetCurrency(), exchangeRate,
              transaction.getCreationDateTime()));
      var transactionStatus = switch (walletResponse.status()) {
        case COMPLETED -> Status.COMPLETED;
        case DECLINED -> Status.DECLINED;
      };
      transaction.setStatus(transactionStatus);
      transaction.setUpdateDateTime(LocalDateTime.now());
      response = new LedgerResponse(transaction.getStatus(), walletResponse.reason());
    } else {
      transaction.setStatus(Status.DECLINED);
      transaction.setUpdateDateTime(LocalDateTime.now());
      response = new LedgerResponse(transaction.getStatus(), risk.riskType().getReason());
    }
    log.info("[LEDGER SERVICE] Requested transaction = [{}] was", payload);
    transactionRepository.save(transaction);
    return response;
  }

}