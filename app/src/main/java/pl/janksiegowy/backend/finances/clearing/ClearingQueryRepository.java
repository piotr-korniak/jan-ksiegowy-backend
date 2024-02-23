package pl.janksiegowy.backend.finances.clearing;

import java.time.LocalDate;

public interface ClearingQueryRepository {

    boolean existReceivable( String number, String taxNumber, LocalDate date);
    boolean existPayable( String number, String taxNumber, LocalDate date);
}
