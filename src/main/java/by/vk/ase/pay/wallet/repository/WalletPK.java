package by.vk.ase.pay.wallet.repository;

import by.vk.ase.pay.common.Currency;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletPK implements Serializable {

  private UUID id;
  private Long ownerId;
  private Currency currency;

}
