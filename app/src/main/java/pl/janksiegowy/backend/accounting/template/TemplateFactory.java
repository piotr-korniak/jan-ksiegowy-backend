package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.DocumentType.DocumentTypeVisitor;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TemplateFactory {

    private final AccountingRegisterRepository registers;
    private final TemplateLineFactory line;

    public Template from( TemplateDto source) {
        return update( source, new Template()
                .setTemplateId( UUID.randomUUID())
                .setDate( source.getDate()));
    }

    public Template update( TemplateDto source) {
        return update( source, new Template()
                .setTemplateId( source.getTemplateId()));
    }

    public Template update( TemplateDto source, Template template) {
        Optional.ofNullable( source.getLines())
            .ifPresent( lines-> template.setItems( lines.stream()
                .map( templateLineDto-> line.from( templateLineDto, source.getDocumentType().accept(
                    new DocumentTypeVisitor<TemplateLine>() {
                        @Override public TemplateLine visitSalesInvoice() {
                            return new InvoiceTemplateLine().setFunction(
                                    InvoiceFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitPurchaseInvoice() {
                            return new InvoiceTemplateLine().setFunction(
                                    InvoiceFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitPaymentReceipt() {
                            return paymentTemplateLine( templateLineDto);
                        }
                        @Override public TemplateLine visitPaymentSpending() {
                            return paymentTemplateLine( templateLineDto);
                        }

                        @Override public TemplateLine visitVatStatement() {
                            return new StatementTemplateLine().setFunction(
                                    StatementFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitCitStatement() {
                            return null;
                        }
                        @Override public TemplateLine visitPitStatement() {
                            return null;
                        }

                        @Override public TemplateLine visitEmployeePayslip() {
                            return new PayslipTemplateLine().setFunction(
                                    PayslipFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitNoteIssued() {
                            return new FinanceTemplateLine().setFunction(
                                    FinanceFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitNoteReceived() {
                            return new FinanceTemplateLine().setFunction(
                                    FinanceFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitChargeSettlement() {
                            return new FinanceTemplateLine().setFunction(
                                    FinanceFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitFeeSettlement() {
                            return new FinanceTemplateLine().setFunction(
                                    FinanceFunction.valueOf( templateLineDto.getFunction()));
                        }
                    })).setTemplate( template))
                .collect( Collectors.toList())));

        return registers.findByCode( source.getRegisterCode())
                .map( register -> template.setRegister( register))
                .orElseThrow()
                .setDocumentType( source.getDocumentType())
                .setCode( source.getCode())
                .setName( source.getName());
    }

    private PaymentTemplateLine paymentTemplateLine( TemplateLineDto templateLineDto) {
        return Optional.of( new PaymentTemplateLine()
                        .setFunction( PaymentFunction.valueOf( templateLineDto.getFunction())))
                .map( line-> templateLineDto.getRegisterType()==null? line:
                        line.setRegisterType( PaymentRegisterType.valueOf( templateLineDto.getRegisterType())))
                .get();
    }

}
