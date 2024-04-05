package pl.janksiegowy.backend.statement;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue( "C")
public class CitStatement extends StatementDocument {



}
