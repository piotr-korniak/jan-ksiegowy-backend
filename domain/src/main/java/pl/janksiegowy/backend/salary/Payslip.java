package pl.janksiegowy.backend.salary;

import jakarta.persistence.*;
import pl.janksiegowy.backend.finances.settlement.EmploySettlement;

import java.util.UUID;

@Entity
@Table( name= "PAYSLIPS")
public class Payslip {

    @Id
    @Column( name= "ID")
    private UUID payslipId;

    @OneToOne( mappedBy= "payslip", cascade = CascadeType.ALL)
    protected EmploySettlement settlement;
}
