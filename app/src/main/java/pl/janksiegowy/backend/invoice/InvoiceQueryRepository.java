package pl.janksiegowy.backend.invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryRepository {

    <T> List<T> findBy( Class<T> type);

    <T> Optional<T> findByNumber( Class<T> type, String number);

}
