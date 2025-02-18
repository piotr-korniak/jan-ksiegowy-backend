package pl.janksiegowy.backend.contract.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.salary.contract.ContractType;

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
    String getTaxNumber();
    LocalDate getBegin();
    LocalDate getEnd();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements ContractDto {

        private UUID contractId;

        @JsonProperty( "Number")
        private String number;

        @JsonProperty( "Type")
        private ContractType type;

        @JsonProperty( "Salary")
        private BigDecimal salary;
        private EntityDto entity;

        @JsonProperty( "Tax Number")
        private String taxNumber;

        @JsonProperty( "Begin Date")
        private LocalDate begin;

        @JsonProperty( "End Date")
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
        @Override public String getTaxNumber() {
            return taxNumber;
        }
        @Override public LocalDate getBegin() {
            return begin;
        }
        @Override public LocalDate getEnd() {
            return end;
        }
    }
}
