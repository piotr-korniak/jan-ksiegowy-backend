package pl.janksiegowy.backend.settlement;

import java.util.List;

public interface SettlementQueryRepository {

    <T> List<T> findBy(Class<T> type);

    boolean existsByNumberAndEntityTaxNumber( String number, String taxNumber);
}
