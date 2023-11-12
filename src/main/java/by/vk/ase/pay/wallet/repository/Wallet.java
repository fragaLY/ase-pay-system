package by.vk.ase.pay.wallet.repository;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(schema = "asepay", name = "wallet")
public class Wallet {

  @EmbeddedId
  private WalletPK walletPK;
  private BigDecimal balance;
}