package pl.janksiegowy.backend.invoice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.shared.financial.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonDeserialize( as= InvoiceDto.Proxy.class)
@JsonNaming( PropertyNamingStrategies.SnakeCaseStrategy.class)
//@JsonPropertyOrder({ "invoice_id", "invoice_number", "invoice_date", "issue_date", "due_date", "contact"})
public interface InvoiceDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getDocumentId();


    InvoiceType getType();

    @Value( "#{target.register.registerId}")
    UUID getRegisterRegisterId();

    PeriodDto getPeriod();
    PeriodDto getInvoicePeriod();

//    @JsonProperty( "invoice_number")
    String getNumber();
//    @JsonProperty( "invoice_date")
    LocalDate getInvoiceDate(); // Date of sale (purchase)
//    @JsonProperty( "issue_date")
    LocalDate getIssueDate();   // Date of receipt
//    @JsonProperty( "due_date")
    LocalDate getDueDate();     // Date of due

    EntityDto getEntity();
    @JsonProperty( "line_items")
    List<InvoiceLineDto> getLineItems();

    PaymentMethod getPaymentMetod();

    BigDecimal getSubTotal();
    BigDecimal getTaxTotal();
    BigDecimal getAmount();

    String getCorrection();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements InvoiceDto {

        private UUID invoiceId;
        private InvoiceType type;

        private UUID registerId;

        private PeriodDto period;
        private PeriodDto invoicePeriod;
        private String number;
        private LocalDate invoiceDate;
        private LocalDate issueDate;
        private LocalDate dueDate;
        private EntityDto entity;
        private List<InvoiceLineDto> items;
        private PaymentMethod paymentMetod;
        private BigDecimal subTotal;
        private BigDecimal taxTotal;
        private BigDecimal amount;
        private String correction;

        @Override public UUID getDocumentId() {
            return invoiceId;
        }
        @Override public InvoiceType getType() {
            return type;
        }

        @Override public UUID getRegisterRegisterId() {
            return registerId;
        }

        @Override public PeriodDto getPeriod() {
            return period;
        }
        @Override public PeriodDto getInvoicePeriod() {
            return invoicePeriod;
        }
        @Override public String getNumber() {
            return number;
        }
        @Override public LocalDate getInvoiceDate() {
            return invoiceDate;
        }
        @Override public LocalDate getIssueDate() {
            return issueDate;
        }
        @Override public LocalDate getDueDate() {
            return dueDate;
        }
        @Override public EntityDto getEntity() {
            return entity;
        }
        @Override public List<InvoiceLineDto> getLineItems() {
            return items;
        }
        @Override public PaymentMethod getPaymentMetod() {
            return paymentMetod;
        }

        @Override public BigDecimal getSubTotal() {
            return subTotal;
        }
        @Override public BigDecimal getTaxTotal() {
            return taxTotal;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }

        @Override
        public String getCorrection() {
            return correction;
        }
    }
}
