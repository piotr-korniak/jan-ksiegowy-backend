package pl.janksiegowy.backend.accounting.decree.dto;

import java.math.BigDecimal;

public interface DecreeSummaryDto {

    BigDecimal getDt();
    BigDecimal getCt();

    default BigDecimal getDtBalance() {
        return getDt().compareTo( getCt())>0? getDt().subtract( getCt()): BigDecimal.ZERO;
    }

    default BigDecimal getCtBalance() {
        return getCt().compareTo( getDt())>0? getCt().subtract( getDt()): BigDecimal.ZERO;
    }
}
