package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.accounting.decree.DecreeType.DecreeTypeVisitor;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.note.Note;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.finances.share.Share;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.salary.Payslip;
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
    private final ClearingQueryRepository clearingQuery;


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

    public DecreeDto to( Document document) {
        return document.accept( this);
    }

    public DecreeDto to( PayableStatement statement) {
        return new DecreeFactoryStatement( templates).to( statement);
    }

    protected abstract static class Builder {

        abstract BigDecimal getValue( TemplateLine line);
        abstract Optional<AccountDto> getAccount( TemplateLine line);

        public DecreeMap build( Template template, LocalDate date, String document, UUID decreeId) {
            var decree= new DecreeMap( DecreeDto.create()
                    .type( DecreeType.D)
                    .degreeId( decreeId)
                    .date( date)
                    .document( document)
                    .register( RegisterDto.create()
                            .registerId( template.getRegister().getRegisterId())));

            template.getItems().forEach( templateItem->
                getAccount( templateItem).ifPresent( accountDto->
                    Optional.of( getValue( templateItem))
                            .filter(value-> value.signum()!= 0) // Filtrowanie wartości != 0
                            .ifPresent(value-> decree.getLines().stream()
                                    .filter(line->
                                            line.getAccount().getNumber().equals( accountDto.getNumber()))
                                    .findFirst().ifPresentOrElse( decreeLineDto->
                                            ((DecreeLineDto.Proxy)decreeLineDto)
                                                    .value( decreeLineDto.getValue().add( value)),
                                            ()-> decree.add( DecreeLineDto.create()
                                                    .account( accountDto)
                                                    .value( getValue( templateItem))
                                                    .side( templateItem.getSide())
                                                    .description( templateItem.getDescription()))))));
            return decree;
        }
    }


    @Override public DecreeDto visit( Invoice invoice) {
        return new DecreeFactoryInvoice( templates).to( invoice);
    }

    @Override public DecreeDto visit( Payment payment) {
        return new DecreeFactoryPayment( templates, clearings, clearingQuery).to( payment);
    }

    @Override public DecreeDto visit( Note note) {
        return new DecreeFactoryNote( templates).to( note);
    }

    @Override public DecreeDto visit( Charge charge) {
        return new DecreeFactoryCharge( templates).to( charge);
    }

    @Override public DecreeDto visit( Share share) {
        return DecreeFactoryShare.create( templates).to( share);
    }

    @Override public DecreeDto visit( Payslip payslip) {
        return new DecreeFactoryPayslip( templates).to( payslip);
    }
}
