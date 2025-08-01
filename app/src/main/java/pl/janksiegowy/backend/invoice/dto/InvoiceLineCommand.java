package pl.janksiegowy.backend.invoice.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.invoice.InvoiceLineId;
import pl.janksiegowy.backend.invoice.TaxedAmount;
import pl.janksiegowy.backend.item.dto.ItemDto;

public interface InvoiceLineCommand {

    static Proxy create() {
        return new Proxy();
    }

    InvoiceLineId getInvoiceLineId();
    TaxedAmount getAmount();
    ItemDto getItem();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements InvoiceLineCommand {

        private InvoiceLineId invoiceLineId;
        private TaxedAmount amount;
        private ItemDto item;

        @Override public InvoiceLineId getInvoiceLineId() {
            return invoiceLineId;
        }

        @Override public TaxedAmount getAmount() {
            return amount;
        }

        @Override public ItemDto getItem() {
            return item;
        }
    }
}
