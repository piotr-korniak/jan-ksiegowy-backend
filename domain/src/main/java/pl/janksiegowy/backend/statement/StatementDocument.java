package pl.janksiegowy.backend.statement;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Accessors( chain= true)

@Entity
@SecondaryTable( name= StatementDocument.TABLE_NAME,
        pkJoinColumns= @PrimaryKeyJoinColumn( name="DOCUMENT_ID"))

@Inheritance( strategy= InheritanceType.SINGLE_TABLE)
public abstract class StatementDocument extends Statement {
    static final String TABLE_NAME = "SETTLEMENTS";

    @Column( name= "DATE", table= TABLE_NAME)
    LocalDate settlementDate= LocalDate.now();

    @Column( table= TABLE_NAME)
    LocalDate due= LocalDate.now();

    @Column( table= TABLE_NAME)
    private String number;


    @Column( name= "CT", table= TABLE_NAME)
    private BigDecimal liability;

    @Column( table= TABLE_NAME)
    private BigDecimal dt= BigDecimal.ZERO;

}
