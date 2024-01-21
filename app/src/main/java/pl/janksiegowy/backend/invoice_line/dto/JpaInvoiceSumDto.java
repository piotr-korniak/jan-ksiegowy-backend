package pl.janksiegowy.backend.invoice_line.dto;

import pl.janksiegowy.backend.financial.TaxRate;
import pl.janksiegowy.backend.item.ItemType;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface JpaInvoiceSumDto {

    UUID getInvoiceId();
    String getInvoiceNumber();
    LocalDate getInvoiceDate();
    LocalDate getIssueDate();

    String getEntityName();
    String getTaxNumber();
    String getEntityCountry();

    InvoiceRegisterKind getSalesKind();
    InvoiceRegisterKind getPurchaseKind();

    TaxRate getTaxRate();
    ItemType getItemType();
    BigDecimal getBase();
    BigDecimal getVat();

}
