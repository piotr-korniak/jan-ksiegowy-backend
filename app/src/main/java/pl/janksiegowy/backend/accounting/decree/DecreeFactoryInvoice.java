package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceType;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.item.ItemType;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind.VatRegisterTypeVisitor;
import pl.janksiegowy.backend.shared.Util;


import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandEntityAccount;

@AllArgsConstructor
public class DecreeFactoryInvoice {

    private final TemplateRepository templates;

    private static final Map<InvoiceType, TemplateType> TEMPLATE_MAP= Map.of(
            InvoiceType.S, TemplateType.IS,
            InvoiceType.P, TemplateType.IP
    );

    private TemplateType getTemplateType( InvoiceType type) {
        return TEMPLATE_MAP.get( type);
    }

    public DecreeDto to( Invoice invoice) {

        return templates.findByDocumentTypeAndDate( getTemplateType( invoice.getType()), invoice.getInvoiceDate())
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
                                    //return BigDecimal.ZERO;
                                    return invoice.getRegisterKind().accept( new VatRegisterTypeVisitor<BigDecimal>() {
                                        @Override public BigDecimal visitDomestic() {
                                            return invoice.getSubTotal().add( invoice.getTaxTotal());
                                        }
                                        @Override public BigDecimal visitUnion() {
                                            return invoice.getSubTotal();
                                        }
                                        @Override public BigDecimal visitWorld() {
                                            return invoice.getSubTotal();
                                        }});
                                }

                                @Override public BigDecimal visitKwotaVAT() {
                                    return invoice.getLineItems().stream()
                                            .map( InvoiceLine::getVat)
                                            .reduce( BigDecimal.ZERO, BigDecimal::add);
                                }
                                @Override public BigDecimal visitOdliczenieVAT() {
                                    return invoice.getRegisterKind().accept( new VatRegisterTypeVisitor<BigDecimal>() {
                                        @Override public BigDecimal visitDomestic() {
                                            return BigDecimal.ZERO;
                                        }
                                        @Override public BigDecimal visitUnion() {
                                            return visitKwotaVAT();
                                        }
                                        @Override public BigDecimal visitWorld() {
                                            return visitKwotaVAT();
                                        }});
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

                public Optional<AccountDto> getAccount(TemplateLine line) {
                    var account= line.getAccount();
                    return Stream.of( account.getNumber())
                            .filter(k-> k.matches(".*\\[C].*"))
                            .findFirst()
                            .map(s-> expandEntityAccount( account.getNumber(), invoice.getEntity()))
                            .orElseGet(()-> Optional.of( AccountDto.create()
                                    .name( account.getName())
                                    .number( account.getNumber() // Opcjonalnie, jeśli nic nie pasuje
                                    )));
                }

            }.build( template, Util.min( invoice.getIssueDate(), invoice.getInvoiceDate()),
                    invoice.getNumber(), invoice.getDocumentId()))
            .map( decreeMap-> Optional.ofNullable( invoice.getDecree())
                    .map( decree-> decreeMap.setNumer( decree.getNumber()))
                    .orElseGet(()-> decreeMap))
            .orElseThrow();
    }

}
