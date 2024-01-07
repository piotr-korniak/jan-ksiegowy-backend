package pl.janksiegowy.backend.invoice;

import pl.janksiegowy.backend.invoice.dto.InvoiceDto;

import java.util.List;
import java.util.Optional;

public interface InvoiceQueryRepository {

    <T> List<T> findBy( Class<T> type);

    <T> Optional<T> findBySettlementNumber( Class<T> type, String number);

}
