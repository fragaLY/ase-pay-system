package by.vk.ase.pay.ledger.repository;

import by.vk.ase.pay.common.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(schema = "asepay", name = "transaction")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

  @Id
  private UUID id;
  private Long senderId;
  private Long recipientId;
  private BigDecimal amount;
  private Currency sourceCurrency;
  private Currency targetCurrency;
  private BigDecimal exchangeRate;
  private Status status;
  @CreatedDate
  private LocalDateTime creationDateTime;
  @LastModifiedDate
  private LocalDateTime updateDateTime;

}
