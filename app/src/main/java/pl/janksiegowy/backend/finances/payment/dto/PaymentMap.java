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

    public PaymentMap( PaymentDto payment ) {
        this.payment= payment;
        this.clearings= new ArrayList<>( payment.getClearings());
    }

    public ClearingDto add( ClearingDto clearing) {
        clearings.add( clearing);
        return clearing;
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
    public LocalDate getDate() {
        return payment.getDate();
    }

    @Override
    public BigDecimal getAmount() {
        return payment.getAmount();
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
