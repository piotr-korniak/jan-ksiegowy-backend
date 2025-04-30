package pl.janksiegowy.backend.salary.payslip;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "M")
public class MandatePayslip extends PayrollPayslip {
    // Specyficzne dla umowy zlecenie
}
