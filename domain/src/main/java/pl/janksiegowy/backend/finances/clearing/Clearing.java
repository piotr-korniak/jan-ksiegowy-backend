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
    private UUID receivableId;
    /*
    @ManyToOne( cascade= { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn( name= "RECEIVABLE_ID")
    private Settlement receivable;*/

    @Id
    private UUID payableId;

    /*
    @Id
    @ManyToOne( cascade= { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn( name= "PAYABLE_ID")
    private Settlement payable;*/

    private LocalDate date;
    @Getter
    private BigDecimal amount;

    public Clearing setDate( LocalDate date) {
        this.date= date;
        return this;
    }
/*
    public Settlement getReverse( Settlement settlement) {
        return settlement.getSettlementId().equals( receivable.getSettlementId())? payable: receivable;
    }*/

    public Clearing setSettlement(  BigDecimal amount, UUID receivableId, UUID payableId) {
        this.amount= amount;
        this.receivableId= receivableId;
        this.payableId= payableId;
        return this;
    }


}
