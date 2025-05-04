package pl.janksiegowy.backend.salary.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.contract.dto.ContractDto;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.salary.contract.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface PayslipDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getDocumentId();
    String getNumber();
    LocalDate getDate();
    EntityDto getEntity();
    UUID getEntityEntityId();

    ContractDto getContract();
    UUID getContractContractId();

    Map<WageIndicatorCode, BigDecimal> getElements();

    LocalDate getDueDate();
    BigDecimal getAmount();

    ContractType getContractType();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements PayslipDto {

        private UUID documentId;
        private String number;
        private LocalDate date;
        private LocalDate dueDate;
        private EntityDto entity;
        private UUID entityId;
        private BigDecimal amount;
        private ContractDto contract;
        private UUID contractId;
        private ContractType contractType;

        private Map<WageIndicatorCode, BigDecimal> elements;

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

        @Override public UUID getEntityEntityId() {
            return entityId;
        }

        @Override
        public ContractDto getContract() {
            return contract;
        }

        @Override
        public UUID getContractContractId() {
            return contractId;
        }

        @Override public Map<WageIndicatorCode, BigDecimal> getElements() {
            return elements;
        }

        @Override
        public LocalDate getDueDate() {
            return dueDate;
        }

        @Override
        public BigDecimal getAmount() {
            return amount;
        }

        @Override public ContractType getContractType() {
            return contractType;
        }


    }
}
