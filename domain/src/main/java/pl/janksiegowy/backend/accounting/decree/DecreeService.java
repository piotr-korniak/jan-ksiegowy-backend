package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.salary.payslip.PayrollPayslip;

public interface DecreeService {

    Decree book( Document document);
    Decree book( PayrollPayslip payslip);
    Decree save( Decree decree);
}
