package pl.janksiegowy.backend.salary.payslip;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue( value= "E")
public class EmploymentPayslip extends Payslip {
    // Specyficzne dla umowy o pracę
}
