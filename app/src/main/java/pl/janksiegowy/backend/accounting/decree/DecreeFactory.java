package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.accounting.decree.DecreeType.DecreeTypeVisitor;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.note.Note;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.finances.document.Document.DocumentVisitor;
import pl.janksiegowy.backend.statement.PayableStatement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DecreeFactory implements DocumentVisitor<DecreeDto> {

    protected final TemplateRepository templates;
    protected final AccountingRegisterRepository registers;
    protected final DecreeLineFactory line;
    protected final PeriodFacade period;
    protected final NumeratorFacade numerators;
    protected final ClearingRepository clearings;


    public Decree from( DecreeDto source) {
        return update( source, Optional.ofNullable( source.getDecreeId())
                .map( id-> create( source).setDecreeId( id))
                .orElse( create( source).setDecreeId( UUID.randomUUID())));
    }

    public Decree update( DecreeDto source, Decree decree) {
        Optional.ofNullable( source.getLines())
                .ifPresent( lines-> decree.setLines( lines.stream()
                        .map( decreeLineDto-> line.from( decreeLineDto).setDecree( decree))
                        .collect( Collectors.toList())));

        return registers.findById( source.getRegister().getRegisterId())
                .map( register-> decree.setDate( source.getDate())
                        .setRegister( register)
                        .setDocument( source.getDocument())
                        .setNumber( Optional.ofNullable( source.getNumber())
                                .orElseGet(()-> numerators.increment( NumeratorCode.AC,
                                                            register.getCode(), source.getDate())))
                        .setPeriod( period.findMonthPeriodOrAdd( source.getDate())))
                .orElseThrow();
    }

    private Decree create( DecreeDto source) {
        return source.getType().accept( new DecreeTypeVisitor<Decree>() {
            @Override public Decree visitBookingDecree() {
                return new BasicDecree();
            }
            @Override public Decree visitDocumentDecree() {
                return new DocumentDecree();
            }
        });
    }
/*
    public DecreeDto to( Payslip payslip) {

        return templates.findByDocumentTypeAndDate( TemplateType.EP, payslip.getDate())
                .map( template-> new Builder() {
                    @Override public BigDecimal getValue( TemplateLine line ) {
                        return ((PayslipTemplateLine)line).getFunction()
                                .accept( new PayslipFunction.PayslipFunctionVisitor<BigDecimal>() {
                            @Override public BigDecimal visitSkladkaPracownika() {
                                return payslip.getInsuranceEmployee();
                            }
                            @Override public BigDecimal visitSkladkaPracodawcy() {
                                return payslip.getInsuranceEmployer();
                            }
                            @Override public BigDecimal visitWynagrodzenieBrutto() {
                                return payslip.getGross();
                            }

                            @Override public BigDecimal visitUbezpiecznieZdrowotne() {
                                return payslip.getInsuranceHealth();
                            }
                            @Override public BigDecimal visitZaliczkaPIT() {
                                return payslip.getTaxAdvance();
                            }
                            @Override public BigDecimal visitDoWyplaty() {
                                return payslip.getCt();
                            }
                        } );
                    }

                    @Override public AccountDto getAccount( AccountDto.Proxy account) {
                        return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                            case "P"-> account.name( payslip.getEntity().getName())
                                    .number( account.getNumber().replaceAll( "\\[P\\]",
                                            payslip.getEntity().getAccountNumber()));
                            default -> account;
                        };
                    }
                }.build( template, payslip.getDate(), payslip.getNumber(), payslip.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( payslip.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();

    }
*/

    public DecreeDto to( Document document) {
        return document.accept( this);
    }

    public DecreeDto to( PayableStatement statement) {
        return new DecreeFactoryStatement( templates).to( statement);
    }

    @Override public DecreeDto visit( Invoice invoice) {
        return new DecreeFactoryInvoice( templates).to( invoice);
    }

    @Override public DecreeDto visit( Payment payment) {
        return new DecreeFactoryPayment( templates, clearings).to( payment);
    }

    @Override public DecreeDto visit( Note note) {
        return new DecreeFactoryNote( templates).to( note);
    }


    protected abstract static class Builder {

        public abstract BigDecimal getValue( TemplateLine line);
        abstract public AccountDto getAccount( AccountDto.Proxy accountNumber);

        public DecreeMap build( Template template, LocalDate date, String document, UUID decreeId) {
            var decree= new DecreeMap( DecreeDto.create()
                    .type( DecreeType.D)
                    .degreeId( decreeId)
                    .date( date)
                    .document( document)
                    .register( RegisterDto.create()
                            .registerId( template.getRegister().getRegisterId())));

            template.getItems().forEach( templateItem-> {
                var value= getValue( templateItem);
                if( value.signum()!=0) {
                    decree.add( DecreeLineDto.create()
                            .account( getAccount( AccountDto.create()
                                    .number( templateItem.getAccount().getNumber())
                                    .parent( templateItem.getAccount().getNumber())))
                            .description( templateItem.getDescription())
                            .page( templateItem.getPage())
                            .value( value));
                }
            });
            return decree;
        }
    }

}
