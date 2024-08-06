package pl.janksiegowy.backend.salary.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.period.dto.PeriodDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.List;
import java.util.UUID;

public interface PayslipDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getDocumentId();
    String getNumber();
    LocalDate getDate();
    EntityDto getEntity();
    ContractDto getContract();

    List<PayslipLineDto> getPayslipLines();

    LocalDate getDueDate();
    BigDecimal getAmount();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PayslipDto {

        private UUID documentId;
        private String number;
        private LocalDate date;
        private LocalDate dueDate;
        private EntityDto entity;
        private BigDecimal amount;
        private ContractDto contract;

        private List<PayslipLineDto> payslipLines;

        @Override public UUID getDocumentId() {
            return documentId;
        }
        @Override public String getNumber() {
            return number;
        }
        @Override public LocalDate getDate() {
            return date;
        }

        @Override public EntityDto getEntity() {
            return entity;
        }

        @Override
        public ContractDto getContract() {
            return contract;
        }

        @Override public List<PayslipLineDto> getPayslipLines() {
            return payslipLines;
        }

        @Override
        public LocalDate getDueDate() {
            return dueDate;
        }

        @Override
        public BigDecimal getAmount() {
            return amount;
        }


    }
}
