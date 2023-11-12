package by.vk.ase.pay.risk.management.api.payload;

public enum RiskType {
  NONE(""),
  CARD_FRAUD("Card fraud detected"),
  MONEY_LAUNDRY("Potential money laudry detected"),
  COUNTER_TERRORISM_FINANCING("Potential counter terrorism financing detected"),
  UNDEFINED("Risky transaction detected");

  private final String reason;

  RiskType(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
