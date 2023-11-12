package by.vk.ase.pay.wallet.service;

import by.vk.ase.pay.wallet.payload.Status;
import by.vk.ase.pay.wallet.payload.WalletPayload;
import by.vk.ase.pay.wallet.payload.WalletResponse;
import by.vk.ase.pay.wallet.repository.WalletRepository;
import jakarta.validation.constraints.NotNull;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The wallet service for money transferring between accounts inside the ASEPAY platform.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

  private static final int SCALE_OF_TWO = 2;

  private final WalletRepository repository;

  /**
   * Money transferring process between accounts in the ASEPAY platform. During the process handling
   * existing wallets in selected currencies both for sender and recipient, check if sender's
   * balance has enough balance.
   *
   * @param payload the payload for money transferring
   * @return {@link WalletResponse}
   */
  @NotNull
  @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.SERIALIZABLE)
  public WalletResponse transfer(
      @NotNull(message = "The wallet payload should not be null") WalletPayload payload) {
    log.info("[WALLET SERVICE] Starting processing money transferring for payload = [{}]", payload);
    var amountInTargetCurrency = payload.amount().multiply(payload.exchangeRate())
        .setScale(SCALE_OF_TWO, RoundingMode.HALF_UP);
    var senderWallet = repository.findByWalletPK_OwnerIdAndWalletPK_Currency(payload.senderId(),
        payload.sourceCurrency());

    if (senderWallet.isEmpty()) {
      log.error("[WALLET SERVICE] Sender's [{}] wallet in selected currency [{}] was not found",
          payload.senderId(), payload.sourceCurrency());
      return new WalletResponse(payload.senderId(), payload.recipientId(), payload.amount(),
          amountInTargetCurrency, payload.sourceCurrency(), payload.targetCurrency(),
          Status.DECLINED,
          "Sender's wallet in selected currency was not found");
    }

    var senderEntity = senderWallet.get();
    if (senderEntity.getBalance().compareTo(payload.amount()) < 0) {
      log.error("[WALLET SERVICE] Sender's [{}] wallet in selected currency [{}] was not found",
          payload.senderId(), payload.sourceCurrency());
      return new WalletResponse(payload.senderId(), payload.recipientId(), payload.amount(),
          amountInTargetCurrency, payload.sourceCurrency(), payload.targetCurrency(),
          Status.DECLINED,
          "Insufficient sender's funds");
    }

    var recipientWallet = repository.findByWalletPK_OwnerIdAndWalletPK_Currency(
        payload.recipientId(),
        payload.targetCurrency());

    if (recipientWallet.isEmpty()) {
      log.error("[WALLET SERVICE] Recipient's [{}] wallet in selected currency [{}] was not found",
          payload.senderId(), payload.sourceCurrency());
      return new WalletResponse(payload.senderId(), payload.recipientId(), payload.amount(),
          amountInTargetCurrency, payload.sourceCurrency(), payload.targetCurrency(),
          Status.DECLINED,
          "Recipient's wallet in selected currency was not found");
    }

    var recipientEntity = recipientWallet.get();
    senderEntity.setBalance(senderEntity.getBalance().subtract(payload.amount()));
    recipientEntity.setBalance(recipientEntity.getBalance().add(amountInTargetCurrency));
    repository.saveAllAndFlush(List.of(senderEntity, recipientEntity));
    log.info("[WALLET SERVICE] Processing money transferring for payload = [{}] is completed",
        payload);

    return new WalletResponse(payload.senderId(), payload.recipientId(), payload.amount(),
        amountInTargetCurrency, payload.sourceCurrency(), payload.targetCurrency(),
        Status.COMPLETED, null);
  }
}

