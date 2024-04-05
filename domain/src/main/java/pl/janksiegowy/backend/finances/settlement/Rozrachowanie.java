package pl.janksiegowy.backend.finances.settlement;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.clearing.ClearingId;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "CLEARINGS")
@IdClass( ClearingId.class)
public class Rozrachowanie {

    @Id
    @ManyToOne( cascade= { CascadeType.ALL})
    @JoinColumn( name= "RECEIVABLE_ID")
    private Receivable receivable;

    @Id
    @ManyToOne( cascade= { CascadeType.ALL})
    @JoinColumn( name= "PAYABLE_ID")
    private Payable payable;

    private LocalDate date;
    @Getter
    private BigDecimal amount;

    public Rozrachowanie setSettlement( Receivable receivable, Payable payable) {
        receivable.add( this);
        this.receivable= receivable;
        this.payable= payable;
        return this;
    }
}
