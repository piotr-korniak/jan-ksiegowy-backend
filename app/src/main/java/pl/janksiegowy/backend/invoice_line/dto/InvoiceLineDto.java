package pl.janksiegowy.backend.invoice_line.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.shared.financial.TaxMethod;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.item.dto.ItemDto;

import java.math.BigDecimal;
import java.util.UUID;

public interface InvoiceLineDto {

    static Proxy create() {
        return new Proxy();
    }
    @JsonProperty( "line_item_id")
    UUID getId();

    ItemDto getItem();

    BigDecimal getAmount();

    BigDecimal getTax();
    TaxMethod getTaxMetod();
    TaxRate getTaxRate();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements InvoiceLineDto {

        private UUID id;
        private ItemDto item;
        private BigDecimal amount;
        private BigDecimal tax;
        private TaxMethod taxMetod;
        private TaxRate taxRate;

        @Override public UUID getId() {
            return id;
        }
        @Override public ItemDto getItem() {
            return item;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }
        @Override public BigDecimal getTax() {
            return tax;
        }
        @Override public TaxMethod getTaxMetod() {
            return taxMetod;
        }
        @Override public TaxRate getTaxRate() {
            return taxRate;
        }
    }
}
