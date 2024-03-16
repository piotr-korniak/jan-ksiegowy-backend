package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.period.Period;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DecreeLineQueryRepository {

    default BigDecimal turnoverDt( Period period, String name) {
        return sumValueByTypeAndAccountNameLike( DecreeDtLine.class, name, period.getBegin(), period.getEnd());
    }

    default BigDecimal turnoverCt( Period period, String name) {
        return sumValueByTypeAndAccountNameLike( DecreeCtLine.class, name, period.getBegin(), period.getEnd());
    }

    BigDecimal sumValueByTypeAndAccountNameLike( Class<? extends DecreeLine> type, String name,
                                                 LocalDate periodStart, LocalDate periodEnd);
}
