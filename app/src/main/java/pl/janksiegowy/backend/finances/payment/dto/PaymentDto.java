package pl.janksiegowy.backend.finances.payment.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.finances.settlement.Settlement;
import pl.janksiegowy.backend.finances.settlement.dto.SettlementDto;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentDto {

    static Proxy create() {
        return new Proxy();
    }

    public UUID getPaymentId();
    public PaymentType getType();
    public String getSettlementNumber();
    public LocalDate getSettlementDate();
    public BigDecimal getSettlementCt();
    public RegisterDto getRegister();
    public EntityDto getSettlementEntity();

    public SettlementDto getSettlement();
    public List<Clearing> getSettlementReceivable();
    public List<Clearing> getSettlementPayable();

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
        private List<Clearing> receivable;
        private List<Clearing> payable;

        @Override public UUID getPaymentId() {
            return paymentId;
        }
        @Override public PaymentType getType() {
            return type;
        }
        @Override public String getSettlementNumber() {
            return number;
        }
        @Override public LocalDate getSettlementDate() {
            return date;
        }
        @Override public BigDecimal getSettlementCt() {
            return amount;
        }
        @Override public RegisterDto getRegister() {
            return register;
        }
        @Override public EntityDto getSettlementEntity() {
            return entity;
        }

        @Override
        public SettlementDto getSettlement() {
            return null;
        }

        @Override public List<Clearing> getSettlementReceivable() {
            return receivable;
        }

        @Override
        public List<Clearing> getSettlementPayable() {
            return payable;
        }
    }
}
