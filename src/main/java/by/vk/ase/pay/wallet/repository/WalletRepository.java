package by.vk.ase.pay.wallet.repository;

import by.vk.ase.pay.common.Currency;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, WalletPK> {

  Optional<Wallet> findByWalletPK_OwnerIdAndWalletPK_Currency(Long ownerId, Currency currency);

}
