package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.item.ItemType;


import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
public class DecreeFactoryInvoice implements InvoiceTypeVisitor<TemplateType> {

    private final TemplateRepository templates;

    public DecreeDto to( Invoice invoice) {

        return templates.findByDocumentTypeAndDate( invoice.getType().accept( this), invoice.getInvoiceDate())
                .map( template-> new DecreeFactory.Builder() {
                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((InvoiceTemplateLine)line).getFunction()
                                .accept( new InvoiceFunction.InvoiceFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitKwotaUslugKUP() {
                                        return invoice.getLineItems().stream()
                                                .filter( line-> ItemType.S== line.getItem().getType())
                                                .map( InvoiceLine::getCit).reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                    @Override public BigDecimal visitKwotaUslugNUP() {
                                        return invoice.getLineItems().stream()
                                                .filter( line-> ItemType.S== line.getItem().getType())
                                                .map( line-> line.getAmount().add( line.getTax())
                                                        .subtract( line.getVat()).subtract( line.getCit()))
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                    @Override public BigDecimal visitKwotaBrutto() {
                                        return invoice.getSubTotal().add( invoice.getTaxTotal());
                                    }
                                    @Override public BigDecimal visitKwotaVAT() {
                                        return invoice.getLineItems().stream()
                                                .map( line-> line.getVat())
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                    @Override public BigDecimal visitKwotaMaterialowKUP() {
                                        return invoice.getLineItems().stream()
                                                .filter( line-> ItemType.M== line.getItem().getType())
                                                .map( InvoiceLine::getCit).reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                    @Override public BigDecimal visitKwotaMaterialowNUP() {
                                        return invoice.getLineItems().stream()
                                                .filter( line-> ItemType.M== line.getItem().getType())
                                                .map( line-> line.getAmount().add( line.getTax())
                                                        .subtract( line.getVat()).subtract( line.getCit()))
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }

                                    @Override public BigDecimal visitKwotaPozostaleKup() {
                                        return invoice.getLineItems().stream()
                                                .filter( line-> ItemType.R== line.getItem().getType())
                                                .map( InvoiceLine::getCit).reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                });
                    }
                    @Override public AccountDto getAccount( AccountDto.Proxy account) {
                        return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                            case "P"-> account.name( invoice.getEntity().getName())
                                    .number( account.getNumber().replaceAll( "\\[P\\]",
                                            invoice.getEntity().getAccountNumber()));
                            default -> account;
                        };
                    }
                }.build( template, invoice.getInvoiceDate(), invoice.getNumber(), invoice.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( invoice.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    @Override public TemplateType visitSalesInvoice() {
        return TemplateType.IS;
    }
    @Override public TemplateType visitPurchaseInvoice() {
        return TemplateType.IP;
    }
}
