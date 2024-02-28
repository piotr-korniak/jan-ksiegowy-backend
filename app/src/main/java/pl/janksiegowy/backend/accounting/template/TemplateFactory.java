package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.template.dto.TemplateDto;
import pl.janksiegowy.backend.accounting.template.DocumentType.DocumentTypeVisitor;
import pl.janksiegowy.backend.finances.payment.PaymentType;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TemplateFactory implements DocumentTypeVisitor<Template> {

    private final AccountingRegisterRepository registers;
    private final TemplateLineFactory line;

    public Template from( TemplateDto source) {
        return update( source, source.getType().accept( this)
                .setTemplateId( UUID.randomUUID())
                .setDate( source.getDate()));
    }

    public Template update( TemplateDto source) {
        return update( source, source.getType().accept( this)
                .setTemplateId( source.getTemplateId()));
    }

    public Template update( TemplateDto source, Template template) {

        Optional.ofNullable( source.getLines())
            .ifPresent( lines-> template.setItems( lines.stream()
                .map( templateLineDto-> line.from( templateLineDto).setTemplate( template ))
                .collect( Collectors.toList())));

        return registers.findByCode( source.getRegister().getCode())
                .map( register -> template.setRegister( register))
                .orElseThrow()
                .setCode( source.getCode())
                .setName( source.getName());
    }

    @Override public Template visitSalesInvoice() {
        return null;
    }

    @Override public Template visitPurchaseInvoice() {
        return null;
    }

    @Override public Template visitVatStatement() {
        return null;
    }

    @Override public Template visitCitStatement() {
        return null;
    }

    @Override public Template visitPitStatement() {
        return null;
    }

    @Override public Template visitBankReceipt() {
        return new BankAccountTemplate().setKind( PaymentType.R).setContext( SettlementType.P);
    }
    @Override public Template visitBankSpend() {
        return new BankAccountTemplate().setKind( PaymentType.S).setContext( SettlementType.P);
    }
    @Override public Template visitCashReceipt() {
        return new CashAccountTemplate().setKind( PaymentType.R).setContext( SettlementType.P);
    }
    @Override public Template visitCashSpend() {
        return new CashAccountTemplate().setKind( PaymentType.S).setContext( SettlementType.P);
    }
}
