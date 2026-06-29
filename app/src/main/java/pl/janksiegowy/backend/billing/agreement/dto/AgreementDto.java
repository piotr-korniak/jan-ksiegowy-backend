package pl.janksiegowy.backend.billing.agreement.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.billing.common.BillingType;
import pl.janksiegowy.backend.entity.dto.EntityDto;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public interface AgreementDto {

    static AgreementDto.Proxy create() {
        return new Proxy();
    }

    UUID getAgreementId();
    LocalDate getDate();

    EntityDto getEntity();
    long getBillingTypeMask();

    public Set<BillingType> getBillingTypes();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements AgreementDto {

        private UUID agreementId;
        private LocalDate date;
        private EntityDto entity;
        private long billingTypeMask;

        @Override public UUID getAgreementId() {
            return agreementId;
        }
        @Override public LocalDate getDate() {
            return date;
        }

        @Override public EntityDto getEntity() {
            return entity;
        }
        @Override public long getBillingTypeMask() {
            return billingTypeMask;
        }

        @Override
        public Set<BillingType> getBillingTypes() {
            return BillingType.getBillingTypes( billingTypeMask);
        }

    }
}
