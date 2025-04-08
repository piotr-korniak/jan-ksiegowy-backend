package pl.janksiegowy.backend.invoice.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.shared.financial.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonDeserialize( as= InvoiceCsv.Proxy.class)
public interface InvoiceCsv {

    InvoiceType getType();
    String getRegisterCode();
    String getNumber();
    String getTaxNumber();
    Country getCountry();
    BigDecimal getAmount();
    LocalDate getIssuedDate();
    LocalDate getInvoicedDate();
    LocalDate getDueDate();
    PaymentMethod getPaymentMethod();
    boolean isPaymentMethod();
    String getCorrection();
    boolean isCorrection();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements InvoiceCsv {

        @JsonCreator
        public Proxy( @JsonProperty( "Tax Number") String taxNumber) {
            this.taxNumber= taxNumber.replaceAll("[^a-zA-Z0-9]", "");

            if (!this.taxNumber.matches("\\d+")) {
                this.country= Country.valueOf( this.taxNumber.substring(0, 2));
                this.taxNumber= this.taxNumber.substring(2);
            }
        }

        @JsonProperty( "Type")
        InvoiceType type;

        @JsonProperty( "Register Code")
        String registerCode;

        @JsonProperty( "Number")
        String number;

        @JsonProperty( "Tax Number")
        String taxNumber;

        Country country= Country.PL;

        @JsonProperty( "Amount")
        BigDecimal amount;

        @JsonProperty( "Issue Date")
        LocalDate issueDate;

        @JsonProperty( "Invoice Date")
        LocalDate invoiceDate;

        @JsonProperty( "Due Date")
        LocalDate dueDate;

        @JsonProperty( "Payment Method")
        @JsonDeserialize( converter= PaymentMethodDeserializer.class)
        PaymentMethod paymentMethod;

        @JsonProperty( "Correction")
        String correction;

        @Override public InvoiceType getType() {
            return type;
        }

        @Override public String getRegisterCode() {
            return registerCode;
        }

        @Override public String getNumber() {
            return number;
        }

        @Override public String getTaxNumber() {
            return taxNumber;
        }

        @Override public Country getCountry() {
            return country;
        }

        @Override public BigDecimal getAmount() {
            return amount;
        }

        @Override public LocalDate getIssuedDate() {
            return issueDate;
        }

        @Override public LocalDate getInvoicedDate() {
            return invoiceDate;
        }

        @Override public LocalDate getDueDate() {
            return dueDate;
        }

        @Override public PaymentMethod getPaymentMethod() {
            return paymentMethod;
        }

        public boolean isPaymentMethod() {
            return paymentMethod != null;
        }

        @Override
        public String getCorrection() {
            return correction;
        }

        @Override
        public boolean isCorrection() {
            return correction != null;
        }
    }
}
