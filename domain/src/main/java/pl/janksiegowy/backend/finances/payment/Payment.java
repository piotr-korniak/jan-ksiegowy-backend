package pl.janksiegowy.backend.finances.payment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.register.payment.PaymentRegister;

import java.util.List;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@SecondaryTable( name= Payment.TABLE_NAME, pkJoinColumns= @PrimaryKeyJoinColumn( name="ID"))
public abstract class Payment extends Document {
    static final String TABLE_NAME = "PAYMENTS";

    @ManyToOne
    @JoinColumn( table= TABLE_NAME)
    private PaymentRegister register;

    public PaymentType getType() {
        return PaymentType.valueOf( getClass().getAnnotation( DiscriminatorValue.class).value());
    }

 //   public abstract Document setClearings( List<Clearing> clearings);

}
