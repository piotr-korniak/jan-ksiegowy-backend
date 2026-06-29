package pl.janksiegowy.backend.billing.group.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.billing.common.BillingType;
import pl.janksiegowy.backend.entity.dto.EntityDto;

public interface BillingGroupItemDto {

    static Proxy create() {
        return new Proxy();
    }

    BillingType getBillingType();
    EntityDto getEntity();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements BillingGroupItemDto {

        private BillingType billingType;
        private EntityDto entity;

        @Override public BillingType getBillingType() {
            return billingType;
        }

        @Override public EntityDto getEntity() {
            return entity;
        }
    }
}