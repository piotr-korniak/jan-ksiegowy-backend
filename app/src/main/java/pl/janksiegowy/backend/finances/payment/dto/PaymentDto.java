package pl.janksiegowy.backend.finances.payment.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface PaymentDto {

    static Proxy create() {
        return new Proxy();
    }

    public UUID getPaymentId();
    public PaymentType getType();
    public String getNumber();
    public LocalDate getPaymentDate();
    public BigDecimal getAmount();
    public RegisterDto getRegister();
    public EntityDto getEntity();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PaymentDto {

        private UUID paymentId;
        private PaymentType type;
        private String number;
        private LocalDate date;
        private BigDecimal amount;
        private RegisterDto register;
        private EntityDto entity;

        @Override public UUID getPaymentId() {
            return paymentId;
        }
        @Override public PaymentType getType() {
            return type;
        }
        @Override public String getNumber() {
            return number;
        }
        @Override public LocalDate getPaymentDate() {
            return date;
        }
        @Override public BigDecimal getAmount() {
            return amount;
        }
        @Override public RegisterDto getRegister() {
            return register;
        }
        @Override public EntityDto getEntity() {
            return entity;
        }
    }
}
