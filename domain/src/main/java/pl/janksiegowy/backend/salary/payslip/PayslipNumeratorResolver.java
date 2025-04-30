package pl.janksiegowy.backend.salary.payslip;

import pl.janksiegowy.backend.salary.contract.ContractType;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;

public class PayslipNumeratorResolver implements ContractType.ContractTypeVisitor<NumeratorCode> {

    public static final PayslipNumeratorResolver INSTANCE= new PayslipNumeratorResolver();

    private PayslipNumeratorResolver() {}

    @Override public NumeratorCode visitEmploymentContract() {
        return NumeratorCode.PE;
    }

    @Override public NumeratorCode visitMandateContract() {
        return NumeratorCode.PM;
    }

    @Override public NumeratorCode visitWorkContract() {
        return NumeratorCode.PW;
    }
}
