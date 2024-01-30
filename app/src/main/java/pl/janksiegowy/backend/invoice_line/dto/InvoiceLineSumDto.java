package pl.janksiegowy.backend.invoice_line.dto;

import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.item.ItemType;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.math.BigDecimal;
import java.util.UUID;

public interface InvoiceLineSumDto {

    InvoiceRegisterKind getKind();
    TaxRate getRate();
    BigDecimal getBase();
    BigDecimal getVat();
    ItemType getType();

    UUID getInvoiceId();
}
