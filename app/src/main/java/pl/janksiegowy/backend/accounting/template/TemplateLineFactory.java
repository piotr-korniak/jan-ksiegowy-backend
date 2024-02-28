package pl.janksiegowy.backend.accounting.template;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.AccountRepository;
import pl.janksiegowy.backend.accounting.template.dto.TemplateLineDto;
import pl.janksiegowy.backend.accounting.template.TemplateType.TemplateTypeVisitor;

import java.util.Optional;

@AllArgsConstructor
public class TemplateLineFactory {

    private final AccountRepository accounts;

    public TemplateLine from( TemplateLineDto source) {

        return accounts.findByNumber( source.getAccount().getNumber())
                .map( account-> Optional.ofNullable( source.getId())
                    .map( id-> create( source).setId( id))
                    .orElse( create( source))
                        .setPage( source.getPage())
                        .setAccount( account))
                .orElseThrow();
    }

    private TemplateLine create( TemplateLineDto source) {
        return source.getType().accept( new TemplateTypeVisitor<TemplateLine>() {
            @Override public TemplateLine visitBankTemplate() {
                return new PaymentTemplateLine()
                        .setFunction( PaymentFunction.valueOf( source.getFunction()));
            }
            @Override public TemplateLine visitCashTemplate() {
                return new PaymentTemplateLine()
                        .setFunction( PaymentFunction.valueOf( source.getFunction()));
            }
            @Override public TemplateLine visitSalesInvoiceTemplate() {
                return null;
            }

            @Override public TemplateLine visitPeriodInvoiceTemplate() {
                return null;
            }
        });
    }
}
