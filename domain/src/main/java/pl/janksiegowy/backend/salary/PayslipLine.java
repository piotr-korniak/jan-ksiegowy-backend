package pl.janksiegowy.backend.salary;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import pl.janksiegowy.backend.statement.Statement;
import pl.janksiegowy.backend.statement.StatementItemCode;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Accessors( chain= true)
@Getter

@Entity
@Table( name= "PAYSLIPS_LINES")
public class PayslipLine {

    @Id
    @UuidGenerator
    @Column( name= "ID")
    private UUID payslipLineId;

    @ManyToOne
    @JoinColumn( name= "PAYSLIP_ID")
    private Payslip payslip;

    @Enumerated( EnumType.STRING)
    private PayslipItemCode itemCode;

    private BigDecimal amount;
}
