package pl.janksiegowy.backend.finances.clearing;

import jakarta.persistence.*;
import lombok.Getter;
import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter

@Entity
@Table( name= "CLEARINGS")
@IdClass( ClearingId.class)
public class Clearing {

    @Id
    @ManyToOne( cascade= { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn( name= "RECEIVABLE_ID")
    private Settlement receivable;

    @Id
    @ManyToOne( cascade= { CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn( name= "PAYABLE_ID")
    private Settlement payable;

    private LocalDate date;
    @Getter
    private BigDecimal amount;

    public Clearing setDate( LocalDate date) {
        this.date= date;
        return this;
    }

    public Settlement getReverse( Settlement settlement) {
        return settlement.getSettlementId().equals( receivable.getSettlementId())? payable: receivable;
    }

    public Clearing setSettlement(  BigDecimal amount, Settlement receivable, Settlement payable) {
        this.amount= amount;
        this.receivable= receivable.setCt( receivable.getCt().add( amount));
        this.payable= payable.setDt( payable.getDt().add( amount));
        return this;
    }


}
