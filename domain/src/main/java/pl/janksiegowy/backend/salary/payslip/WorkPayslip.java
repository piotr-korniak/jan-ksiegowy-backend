package pl.janksiegowy.backend.salary.payslip;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "W")
public class WorkPayslip extends PayrollPayslip {
    // Specyficzne dla umowy o dzieło
}
