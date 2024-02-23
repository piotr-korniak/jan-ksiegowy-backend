package pl.janksiegowy.backend.finances.clearing;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.settlement.Settlement;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table( name= "CLEARINGS")

@IdClass( ClearingId.class)
public class Clearing {

    @Id
    @ManyToOne( cascade= { CascadeType.ALL})
    @JoinColumn( name= "RECEIVABLE_ID")
    private Settlement receivable;

    @Id
    @ManyToOne( cascade= { CascadeType.ALL})
    @JoinColumn( name= "PAYABLE_ID")
    private Settlement payable;

    private LocalDate date;
    private BigDecimal amount;

    public Clearing setDate( LocalDate date) {
        this.date= date;
        return this;
    }

    public Clearing setSettlement(  BigDecimal amount, Settlement receivable, Settlement payable) {
        this.amount= amount;
        this.receivable= receivable.setCt( receivable.getCt().add( amount));
        this.payable= payable.setDt( payable.getDt().add( amount));
        return this;
    }


}
