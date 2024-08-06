package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.accounting.decree.dto.DecreeSumDto;
import pl.janksiegowy.backend.period.Period;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DecreeLineQueryRepository {

    default BigDecimal parentBalanceDt( String accountNumber, LocalDate periodStart, LocalDate periodEnd) {
        return sumValueByParentAccountNumber( accountNumber, periodStart, periodEnd).getDtBalance();
    }

    default BigDecimal balanceDtLike( String accountNumber, LocalDate periodStart, LocalDate periodEnd) {
        return sumValueByAccountNumberLike( accountNumber, periodStart, periodEnd).getDtBalance();
    }

    default BigDecimal balanceCtLike( String accountNumber, LocalDate periodStart, LocalDate periodEnd) {
        return sumValueByAccountNumberLike( accountNumber, periodStart, periodEnd).getCtBalance();
    }

    default BigDecimal developedBalanceDt( String accountNumber, LocalDate periodStart, LocalDate periodEnd){
        return sumValueByParentAccountNumberGroupByAccount( accountNumber, periodStart, periodEnd).getDt();
    }

    default BigDecimal developedBalanceCt( String accountNumber, LocalDate periodStart, LocalDate periodEnd) {
        return sumValueByParentAccountNumberGroupByAccount( accountNumber, periodStart, periodEnd).getCt();
    }

    default BigDecimal turnoverDt( String name, LocalDate periodStart, LocalDate periodEnd) {
        return sumValueByTypeAndAccountNameLike( DecreeDtLine.class, name, periodStart, periodEnd);
    }

    default BigDecimal turnoverDt( Period period, String name) {
        return sumValueByTypeAndAccountNameLike( DecreeDtLine.class, name, period.getBegin(), period.getEnd());
    }

    default BigDecimal turnoverCt( Period period, String name) {
        return sumValueByTypeAndAccountNameLike( DecreeCtLine.class, name, period.getBegin(), period.getEnd());
    }

    BigDecimal sumValueByTypeAndAccountNameLike( Class<? extends DecreeLine> type, String name,
                                                 LocalDate periodStart, LocalDate periodEnd);

    DecreeSumDto sumValueByAccountNumberLike(
            String accountNumber, LocalDate periodStart, LocalDate periodEnd);

    DecreeSumDto sumValueByParentAccountNumberGroupByAccount(
            String accountNumber, LocalDate periodStart, LocalDate periodEnd);

    DecreeSumDto sumValueByParentAccountNumber(
            String accountNumber, LocalDate periodStart, LocalDate periodEnd);

}

