package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.accounting.decree.DecreeType.DecreeTypeVisitor;
import pl.janksiegowy.backend.accounting.decree.factory.CloseMonthFactory;
import pl.janksiegowy.backend.accounting.decree.factory.PayslipSubFactory;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.finances.charge.Charge;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.document.Document;
import pl.janksiegowy.backend.finances.notice.Note;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.share.Share;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.RegisterRepository;
import pl.janksiegowy.backend.salary.PayslipRepository;
import pl.janksiegowy.backend.salary.payslip.Payslip;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.finances.document.Document.DocumentVisitor;
import pl.janksiegowy.backend.declaration.PayableDeclaration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DecreeFactory implements DocumentVisitor<DecreeDto>, DecreeTypeVisitor<Decree> {

    protected final TemplateRepository templates;
    protected final RegisterRepository registerRepository;
    protected final DecreeLineFactory line;
    protected final PeriodFacade period;
    protected final NumeratorFacade numerators;
    protected final ClearingRepository clearings;
    private final ClearingQueryRepository clearingQuery;
    private final PayslipSubFactory payslipSubFactory;
    private final CloseMonthFactory closeMonthFactory;
    private final PayslipRepository payslipRepository;

    public Decree from( DecreeDto source) {
        return update( source, source.getType().accept( this)
                .setDecreeId( Optional.ofNullable( source.getDecreeId())
                        .orElseGet( UUID::randomUUID)));
    }

    public Decree update( DecreeDto source, Decree decree) {
        Optional.ofNullable( source.getLines())
                .ifPresent( lines-> decree.setLines( lines.stream()
                        .map( decreeLineDto-> line.from( decreeLineDto).setDecree( decree))
                        .collect( Collectors.toList())));

        return registerRepository.findAccountRegisterByRegisterId( source.getRegisterRegisterId())
                .map( register-> decree.setRegister( register)
                        .setDate( source.getDate())
                        .setDocument( source.getDocument())
                        .setNumber( Optional.ofNullable( source.getNumber())
                                .orElseGet(()-> numerators.increment( NumeratorCode.AC,
                                        register.getCode(), source.getDate())))
                        .setPeriod( period.findMonthPeriodOrAdd( source.getDate())))
                .orElseThrow(()-> new NoSuchElementException( "Account Register not found!"));
    }


    public DecreeDto to( Document document) {
        return document.accept( this);
    }

    public DecreeDto to( PayableDeclaration statement) {
        return new DecreeFactoryStatement( templates).to( statement);
    }

    public DecreeDto to( MonthPeriod month) {
        return closeMonthFactory.create( month);
    }

    @Override public Decree visitDocumentDecree() {
        return new DocumentDecree();
    }

    @Override public Decree visitStatementDecree() {
        return new StatementDecree();
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
                    .registerId( template.getRegister().getRegisterId()));

            template.getItems().forEach( templateItem->
                getAccount( templateItem).ifPresent( accountDto->
                    Optional.of( getValue( templateItem))
                            .filter(value-> value.signum()!= 0) // Filtrowanie wartości != 0
                            .ifPresent(value-> decree.getLines().stream()
                                    .filter(line-> line.getSide()==( templateItem.getSide())&&
                                            line.getAccount().getNumber().equals( accountDto.getNumber()))
                                    .findFirst().ifPresentOrElse( decreeLineDto->
                                            ((DecreeLineDto.Proxy)decreeLineDto)
                                                    .value( decreeLineDto.getValue().add( value)),
                                            ()-> decree.add( DecreeLineDto.create()
                                                    .account( accountDto)
                                                    .value( value)
                                                    .side( templateItem.getSide())
                                                    .description( templateItem.getDescription()))))));
            return decree;
        }
    }


    @Override public DecreeDto visit( Invoice invoice) {
        return new DecreeFactoryInvoice( templates).to( invoice);
    }

    @Override public DecreeDto visit( Payment payment) {
        return new DecreeFactoryPayment( templates, clearings, clearingQuery, payslipRepository).to( payment);
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

    @Override
    public DecreeDto visit( Payslip payslip) {
        return payslipSubFactory.create( payslip);
    }


}
