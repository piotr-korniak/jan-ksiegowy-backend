package pl.janksiegowy.backend.accounting.decree.dto;

import java.math.BigDecimal;

public interface DecreeBalanceDto {

    String getNumber();
    String getName();
    BigDecimal getOpeningDebitTurnover();
    BigDecimal getOpeningCreditTurnover();
    default BigDecimal getOpeningDebitBalance() {
        return getOpeningDebitTurnover().compareTo( getOpeningCreditTurnover())==1?
                getOpeningDebitTurnover().subtract( getOpeningCreditTurnover()): BigDecimal.ZERO;
    };
    default BigDecimal getOpeningCreditBalance() {
        return getOpeningCreditTurnover().compareTo( getOpeningDebitTurnover())==1?
                getOpeningCreditTurnover().subtract( getOpeningDebitTurnover()): BigDecimal.ZERO;
    };
    BigDecimal getDebitTurnover();
    BigDecimal getCreditTurnover();
    BigDecimal getCumulativeDebitTurnover();
    BigDecimal getCumulativeCreditTurnover();
    default BigDecimal getClosingDebitBalance() {
        return getCumulativeDebitTurnover().compareTo( getCumulativeCreditTurnover())==1?
                getCumulativeDebitTurnover().subtract( getCumulativeCreditTurnover()): BigDecimal.ZERO;
    }
    default BigDecimal getClosingCreditBalance() {
        return getCumulativeCreditTurnover().compareTo( getCumulativeDebitTurnover())==1?
                getCumulativeCreditTurnover().subtract( getCumulativeDebitTurnover()): BigDecimal.ZERO;
    }
}
