package pl.janksiegowy.backend.finances.document;

import jakarta.persistence.*;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.finances.payment.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table( name= "SETTLEMENTS")

@Inheritance( strategy= InheritanceType.JOINED)
@DiscriminatorColumn( name= "TYPE", discriminatorType= DiscriminatorType.STRING, length= 1)
@DiscriminatorOptions( force= true)
public abstract class Document {

    @Id
    @UuidGenerator
    @Column( name= "DOCUMENT_ID")
    protected UUID documentId;

    private LocalDate date;
    private LocalDate due;

    protected BigDecimal Dt;
    protected BigDecimal Ct;

    public Document setDocumentId( UUID documentId) {
        this.documentId= documentId;
        return this;
    }

    public Document setDates( LocalDate date, LocalDate due) {
        this.date= date;
        this.due= due;
        return this;
    }

    public abstract Document setAmount( BigDecimal amount );

}
