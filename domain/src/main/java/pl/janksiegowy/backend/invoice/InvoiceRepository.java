package pl.janksiegowy.backend.invoice;

import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository {

    Invoice save( Invoice invoice);
    Optional<Invoice> findByInvoiceId( UUID invoiceId);
}
