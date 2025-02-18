package pl.janksiegowy.backend.accounting.decree;

import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.salary.payslip.Payslip;

public interface DecreeService {

    Decree book( Document document);
    Decree book( Payslip payslip);
    Decree save( Decree decree);
}
