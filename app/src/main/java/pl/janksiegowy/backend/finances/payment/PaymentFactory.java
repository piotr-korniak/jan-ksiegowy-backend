package pl.janksiegowy.backend.finances.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.janksiegowy.backend.entity.EntityRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingFactory;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.finances.payment.dto.PaymentDto;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.payment.PaymentRegisterRepository;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class PaymentFactory {

    private final PaymentRegisterRepository registers;
    private final NumeratorFacade numerators;
    private final PeriodFacade periods;
    private final EntityRepository entities;
    private final ClearingFactory clearing;

    public Payment from( PaymentDto source) {

        return (Payment) registers.findByCode( source.getRegister().getCode())
                .map( register-> source.getType().accept( new PaymentTypeVisitor<Payment>() {
                    @Override public Payment visitPaymentReceipt() {
                        return (Payment) new PaymentReceipt()
                            .setNumber( Optional.ofNullable( source.getNumber())
                                    .orElseGet( ()-> numerators.increment(
                                            switch ( register.getType()) {
                                                case B-> NumeratorCode.BR;
                                                case C-> NumeratorCode.CR;},
                                            register.getCode(), source.getIssueDate())));
                    }
                    @Override public Payment visitPaymentExpense() {
                        var number= Optional.ofNullable( source.getNumber())
                                .orElseGet( ()-> numerators.increment(
                                        switch ( register.getType()) {
                                            case B-> NumeratorCode.BS;
                                            case C-> NumeratorCode.CS;},
                                        register.getCode(), source.getIssueDate()));
                        return (Payment) new PaymentExpense()
                            .setNumber( number);
                    }
                }).setRegister( register)
                        .setClearings( source.getClearings().stream().map( clearing::from).collect( Collectors.toList()))
                        .setDocumentId( Optional.ofNullable( source.getDocumentId()).orElseGet( UUID::randomUUID))
                        .setDates( source.getIssueDate(), source.getIssueDate())
                        .setAmount( source.getAmount())
                        .setPeriod( periods.findMonthPeriodOrAdd( source.getIssueDate()))
                        .setEntity( entities.findByEntityIdAndDate(
                                source.getEntity().getEntityId(), source.getIssueDate()).orElseThrow()) )
                .orElseThrow();
    }
}
