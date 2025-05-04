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
import pl.janksiegowy.backend.salary.payslip.Payslip;

@Service
@AllArgsConstructor
public class DecreeServiceImpl implements DecreeService {

    private final DecreeFactory decreeFactory;
    private final DecreeRepository decreeRepository;

    @Override public Decree book( Document document) {
        return decreeFactory.from( decreeFactory.to( document));
    }

    @Override public Decree save( Decree decree) {
        return decreeRepository.save( decree);
    }

}
