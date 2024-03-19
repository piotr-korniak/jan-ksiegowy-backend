package pl.janksiegowy.backend.salary.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.salary.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface ContractDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getContractId();
    String getNumber();
    ContractType getType();
    BigDecimal getSalary();
    EntityDto getEntity();
    LocalDate getBegin();
    LocalDate getEnd();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ContractDto {

        private UUID contractId;
        private String number;
        private ContractType type;
        private BigDecimal salary;
        private EntityDto entity;
        private LocalDate begin;
        private LocalDate end;

        @Override public UUID getContractId() {
            return contractId;
        }
        @Override public String getNumber() {
            return number;
        }
        @Override public ContractType getType() {
            return type;
        }
        @Override public BigDecimal getSalary() {
            return salary;
        }
        @Override public EntityDto getEntity() {
            return entity;
        }
        @Override public LocalDate getBegin() {
            return begin;
        }
        @Override public LocalDate getEnd() {
            return end;
        }
    }
}
