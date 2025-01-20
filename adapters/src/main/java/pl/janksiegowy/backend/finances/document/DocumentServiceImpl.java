package pl.janksiegowy.backend.finances.document;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.notice.Note;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.payment.PaymentRepository;
import pl.janksiegowy.backend.finances.settlement.SettlementService;
import pl.janksiegowy.backend.finances.share.Share;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.salary.Payslip;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    final private DocumentRepository documentRepository;
    private final ClearingRepository clearingRepository;
    private final SettlementService settlementService;
    @Lazy
    private PaymentRepository paymentRepository;

    @Override
    @Transactional( transactionManager= "companyTransactionManager")
    public void deleteDocument( UUID documentId) {
        settlementService.deleteSettlement( documentId);
        documentRepository.findByDocumentId( documentId)
            .ifPresentOrElse(document-> {
                System.out.println( "Document: "+ document.getNumber());

                document.accept( new Document.DocumentVisitor<Void>(){
                    @Override
                    public Void visit(Invoice invoice) {
                        return null;
                    }

                    @Override public Void visit(Payment payment) {
                        paymentRepository.delete( payment);
                        return null;
                    }

                    @Override
                    public Void visit(Note note) {
                        return null;
                    }

                    @Override
                    public Void visit(Charge charge) {
                        return null;
                    }

                    @Override
                    public Void visit(Share share) {
                        return null;
                    }

                    @Override
                    public Void visit(Payslip payslip) {
                        return null;
                    }
                });

            },
            ()-> new NoSuchElementException( "Document with the given ID does not exist: "+ documentId));
    }
}
