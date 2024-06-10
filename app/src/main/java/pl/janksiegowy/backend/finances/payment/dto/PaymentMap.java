package pl.janksiegowy.backend.finances.payment.dto;

import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaymentMap implements PaymentDto {

    private final PaymentDto payment;
    private final List<ClearingDto> clearings;
    private BigDecimal amount;

    public PaymentMap( PaymentDto payment) {
        this.payment= payment;
        this.amount= payment.getAmount();
        this.clearings= new ArrayList<>( payment.getClearings());
    }

    public PaymentMap add( ClearingDto clearing) {
        clearings.add( clearing);
        return this;
    }

    public PaymentMap add( BigDecimal amount) {
        this.amount= this.amount.add( amount);
        return this;
    }

    @Override public UUID getDocumentId() {
        return payment.getDocumentId();
    }

    @Override
    public PaymentType getType() {
        return payment.getType();
    }

    @Override
    public String getNumber() {
        return payment.getNumber();
    }

    @Override
    public LocalDate getIssueDate() {
        return payment.getIssueDate();
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public RegisterDto getRegister() {
        return payment.getRegister();
    }

    @Override
    public EntityDto getEntity() {
        return payment.getEntity();
    }

    @Override public List<ClearingDto> getClearings() {
        return clearings;
    }


}
