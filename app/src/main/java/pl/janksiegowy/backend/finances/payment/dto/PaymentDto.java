package pl.janksiegowy.backend.finances.payment.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.register.dto.RegisterDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface PaymentDto {

    static Proxy create() {
        return new Proxy();
    }

    public UUID getDocumentId();
    public PaymentType getType();
    public String getNumber();
    public LocalDate getDate();
    public BigDecimal getAmount();
    public RegisterDto getRegister();
    public EntityDto getEntity();

    public List<ClearingDto> getClearings();
/*  public List<Clearing> getSettlementReceivable();
    public List<Clearing> getSettlementPayable();*/


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PaymentDto {

        private UUID documentId;
        private PaymentType type;
        private String number;
        private LocalDate date;
        private BigDecimal amount= BigDecimal.ZERO;
        private RegisterDto register;
        private EntityDto entity;
        private List<ClearingDto> clearings= new ArrayList<>();
        //private List<Clearing> payable;

        @Override public UUID getDocumentId() {
            return documentId;
        }
        @Override public PaymentType getType() {
            return type;
        }

        @Override public String getNumber() {
            return number;
        }
        @Override public LocalDate getDate() {
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


        @Override public List<ClearingDto> getClearings() {
            return clearings;
        }


    }
}
