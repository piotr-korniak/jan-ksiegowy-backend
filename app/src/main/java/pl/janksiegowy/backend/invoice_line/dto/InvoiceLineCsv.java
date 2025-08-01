package pl.janksiegowy.backend.invoice_line.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record InvoiceLineCsv(
        @JsonProperty( "Invoice Number") String invoiceNumber,
        @JsonProperty( "Item Code") String itemCode,
        @JsonProperty( "Net") BigDecimal net,
        @JsonProperty( "Vat") BigDecimal vat
) {}
