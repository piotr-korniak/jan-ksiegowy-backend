package pl.janksiegowy.backend.billing.agreement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.billing.common.BillingType;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Accessors( chain= true)

@Table( name= "AGREEMENTS")
@jakarta.persistence.Entity
public class Agreement {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY)
    private long id;
    private UUID agreementId; // for history and query

    private LocalDate date;

    @ManyToOne( fetch= FetchType.EAGER)
    protected pl.janksiegowy.backend.entity.Entity entity;

    @Column
    private long billingTypeMask= 0L;

    @Column
    public String status= "ACTIVE";

    public boolean hasType( BillingType type) {
        return BillingType.contains( billingTypeMask, type);
    }

    public Set<BillingType> getBillingTypes() {
        return BillingType.getBillingTypes( billingTypeMask);
    }
}
