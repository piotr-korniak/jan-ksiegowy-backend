package pl.janksiegowy.backend.finances.clearing;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter

@Entity
@Table( name= "CLEARINGS")
@IdClass( ClearingId.class)
public class Clearing {

    @Id
    @Column( name= "RECEIVABLE_ID")
    private UUID receivableId;

    @ManyToOne //( cascade= { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn( name= "RECEIVABLE_ID", updatable= false, insertable= false)
    private Settlement receivable;

    @Id
    @Column( name= "PAYABLE_ID")
    private UUID payableId;

    @ManyToOne //( cascade= { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn( name= "PAYABLE_ID", updatable= false, insertable= false)
    private Settlement payable;

    private LocalDate date;
    @Getter
    private BigDecimal amount;

    public Clearing setDate( LocalDate date) {
        this.date= date;
        return this;
    }

    public Settlement getReverse( UUID settlementId) {
        return settlementId.equals( receivable.getSettlementId())? payable: receivable;
    }

    public Clearing setSettlement(  BigDecimal amount, UUID receivableId, UUID payableId) {
        this.amount= amount;
        this.receivableId= receivableId;
        this.payableId= payableId;
        return this;
    }

}
