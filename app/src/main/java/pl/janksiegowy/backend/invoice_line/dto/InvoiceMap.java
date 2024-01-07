package pl.janksiegowy.backend.invoice_line.dto;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.dto.VatRegisterDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InvoiceMap implements InvoiceDto {

    private final InvoiceDto invoice;
    private final List<InvoiceLineDto> lines;

    public InvoiceMap( InvoiceDto invoice) {
        this.invoice= invoice;
        this.lines= new ArrayList<>( invoice.getLineItems());
    }

    @Override
    public UUID getInvoiceId() {
        return invoice.getInvoiceId();
    }

    @Override
    public InvoiceType getType() {
        return invoice.getType();
    }

    @Override
    public VatRegisterDto getVatRegister() {
        return invoice.getVatRegister();
    }

    @Override
    public PeriodDto getPeriod() {
        return invoice.getPeriod();
    }

    @Override
    public String getNumber() {
        return invoice.getNumber();
    }

    @Override
    public LocalDate getInvoiceDate() {
        return invoice.getInvoiceDate();
    }

    @Override
    public LocalDate getIssueDate() {
        return invoice.getIssueDate();
    }

    @Override
    public LocalDate getDueDate() {
        return invoice.getInvoiceDate();
    }

    @Override
    public EntityDto getEntity() {
        return invoice.getEntity();
    }

    @Override
    public List<InvoiceLineDto> getLineItems() {
        return lines;
    }

    public InvoiceMap add(InvoiceLineDto line) {
        lines.add( line);
        return this;
    }
}
