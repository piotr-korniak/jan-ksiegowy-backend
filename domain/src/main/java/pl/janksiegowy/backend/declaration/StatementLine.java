package pl.janksiegowy.backend.declaration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Accessors( chain= true)
@Getter

@Entity
@Table( name= "STATEMENTS_LINES")
public class StatementLine {

    @Id
    @UuidGenerator
    @Column( name= "ID")
    private UUID statementLineId;

    @ManyToOne
    private Declaration statement;

    @Enumerated( EnumType.STRING)
    private DeclarationElementCode itemCode;

    private BigDecimal amount;
}

