package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.document.Document.DocumentVisitor;
import pl.janksiegowy.backend.finances.notice.Note;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.share.Share;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.salary.payslip.PayslipDocument;
import pl.janksiegowy.backend.salary.payslip.Payslip;

@Service
@AllArgsConstructor
public class DecreeServiceImpl implements DecreeService, DocumentVisitor<DecreeDto> {

    private final DecreeFactory decreeFactory;
    private final DecreeRepository decreeRepository;

    @Override public Decree book( Document document) {
        return decreeFactory.from( document.accept( this));
    }

    @Override
    public Decree book( Payslip payslip) {
        return decreeFactory.from( decreeFactory.to( payslip));
    }

    @Override public Decree save( Decree decree) {
        return decreeRepository.save( decree);
    }


    @Override
    public DecreeDto visit( Invoice invoice) {
        return null;
    }

    @Override
    public DecreeDto visit(Payment payment) {
        return null;
    }

    @Override
    public DecreeDto visit(Note note) {
        return null;
    }

    @Override
    public DecreeDto visit(Charge charge) {
        return null;
    }

    @Override
    public DecreeDto visit(Share share) {
        return null;
    }

    @Override public DecreeDto visit( PayslipDocument payslip) {
        return null;
    }
}
