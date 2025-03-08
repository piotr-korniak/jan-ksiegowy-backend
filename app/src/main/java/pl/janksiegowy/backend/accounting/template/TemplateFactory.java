package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.TemplateType.DocumentTypeVisitor;
import pl.janksiegowy.backend.register.RegisterRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TemplateFactory {

    private final RegisterRepository registerRepository;
    private final TemplateLineFactory line;

    public Template from( TemplateDto source) {
        return update( source, new Template()
                .setTemplateId( Optional.ofNullable( source.getTemplateId())
                        .orElseGet( UUID::randomUUID))
                .setDate( source.getDate()));
    }

    public Template update( TemplateDto source, Template template) {
        Optional.ofNullable( source.getLines())
            .ifPresent( lines-> template.setItems( lines.stream()
                .map( templateLineDto-> line.from( templateLineDto, source.getDocumentType().accept(
                    new DocumentTypeVisitor<TemplateLine>() {
                        @Override public TemplateLine visitSalesInvoice() {
                            return new InvoiceTemplateLine()
                                    .setFunction( InvoiceFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitPurchaseInvoice() {
                            return new InvoiceTemplateLine()
                                    .setFunction( InvoiceFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitPaymentReceipt() {
                            return new PaymentTemplateLine()
                                    .setFunction( PaymentFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitPaymentSpending() {
                            return new PaymentTemplateLine()
                                    .setFunction( PaymentFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitVatStatement() {
                            return new StatementTemplateLine().setFunction(
                                    StatementFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitCitStatement() {
                            return new StatementTemplateLine().setFunction(
                                    StatementFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitPitStatement() {
                            return new StatementTemplateLine().setFunction(
                                    StatementFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override
                        public TemplateLine visitDraStatement() {
                            return new StatementTemplateLine().setFunction(
                                    StatementFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitEmployeePayslip() {
                            return new PayslipTemplateLine().setFunction(
                                    PayslipFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitNoteIssued() {
                            return new FinanceTemplateLine().setFunction(
                                    SettlementFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitNoteReceived() {
                            return new FinanceTemplateLine().setFunction(
                                    SettlementFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitChargeLevy() {
                            return new FinanceTemplateLine().setFunction(
                                    SettlementFunction.valueOf( templateLineDto.getFunction()));
                        }
                        @Override public TemplateLine visitChargeFee() {
                            return new FinanceTemplateLine().setFunction(
                                    SettlementFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitAcquiredShare() {
                            return new FinanceTemplateLine().setFunction(
                                    SettlementFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitDisposedShare() {
                            return new FinanceTemplateLine().setFunction(
                                    SettlementFunction.valueOf( templateLineDto.getFunction()));
                        }

                        @Override public TemplateLine visitMonthClose() {
                            return new CloseMonthTemplateLine().setFunction(
                                    CloseMonthFunction.valueOf( templateLineDto.getFunction()));
                        }
                    })).setTemplate( template))
                .collect( Collectors.toList())));

        return registerRepository.findAccountRegisterByCode( source.getRegisterCode())
                .map( template::setRegister)
                .orElseThrow(()-> new NoSuchElementException( "Register not found: "+ source.getRegisterCode()))
                .setDocumentType( source.getDocumentType())
                .setCode( source.getCode())
                .setName( source.getName())
                .setEntityType( source.getEntityType());
    }


}
