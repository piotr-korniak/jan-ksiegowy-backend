package pl.janksiegowy.backend.salary.dto;

import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PayslipMap implements PayslipDto {

    private final PayslipDto payslip;
    private final List<PayslipLineDto> lines;

    private PayslipMap( PayslipDto payslip) {
        this.payslip= payslip;
        this.lines= Optional.ofNullable( payslip.getLines()).orElseGet( ArrayList::new);
    }

    public static PayslipMap create( PayslipDto payslip) {
        return new PayslipMap( payslip);
    }

    @Override public UUID getDocumentId() {
        return payslip.getDocumentId();
    }

    @Override
    public String getNumber() {
        return payslip.getNumber();
    }

    @Override public LocalDate getDate() {
        return payslip.getDate();
    }

    @Override public EntityDto getEntity() {
        return payslip.getEntity();
    }

    @Override
    public ContractDto getContract() {
        return payslip.getContract();
    }

    @Override
    public List<PayslipLineDto> getLines() {
        return lines;
    }

    @Override
    public LocalDate getDueDate() {
        return payslip.getDueDate();
    }

    @Override public BigDecimal getAmount() {
        return payslip.getAmount();
    }

    public PayslipMap addLine( PayslipLineDto line) {
        lines.add( line);
        return this;
    }
}
