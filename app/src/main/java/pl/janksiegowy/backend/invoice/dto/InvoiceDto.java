package pl.janksiegowy.backend.invoice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.invoice_line.dto.InvoiceLineDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JsonDeserialize( as= InvoiceDto.Proxy.class)
//@JsonPropertyOrder({ "invoice_id", "invoice_number", "invoice_date", "issue_date", "due_date", "contact"})
public interface InvoiceDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getInvoiceId();

    public InvoiceType getType();
    RegisterDto getRegister();

    PeriodDto getPeriod();

//    @JsonProperty( "invoice_number")
    String getNumber();
//    @JsonProperty( "invoice_date")
    LocalDate getInvoiceDate(); // Date of sale or receipt
//    @JsonProperty( "issue_date")
    LocalDate getIssueDate();   // Date of issue or purchase
//    @JsonProperty( "due_date")
    LocalDate getDueDate();     // Date of due

    EntityDto getEntity();
    @JsonProperty( "line_items")
    List<InvoiceLineDto> getLineItems();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements InvoiceDto {

        private UUID invoiceId;
        private InvoiceType type;
        private RegisterDto register;
        private PeriodDto period;
        private String number;
        private LocalDate invoiceDate;
        private LocalDate issueDate;
        private LocalDate dueDate;
        private EntityDto entity;
        private List<InvoiceLineDto> items;

        @Override public UUID getInvoiceId() {
            return invoiceId;
        }

        @Override public InvoiceType getType() {
            return type;
        }

        @Override public RegisterDto getRegister() {
            return register;
        }

        @Override public PeriodDto getPeriod() {
            return period;
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


    }
}
