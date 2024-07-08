package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.accounting.template.PaymentFunction.PaymentFunctionVisitor;
import pl.janksiegowy.backend.entity.Entity;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.finances.settlement.SettlementType;
import pl.janksiegowy.backend.register.payment.PaymentRegisterType;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
public class DecreeFactoryPayment implements PaymentTypeVisitor<TemplateType> {

    private final TemplateRepository templates;
    protected final ClearingRepository clearings;

    public DecreeDto to( Payment payment) {
        return templates.findByDocumentTypeAndDate( payment.getType().accept( this ), payment.getIssueDate())
                .map( template-> new DecreeFactory.Builder() {

                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((PaymentTemplateLine)line).getFunction()
                                .accept( new PaymentFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitWplataNaleznosci() {
                                        return payment.getCt();
                                    }
                                    @Override public BigDecimal visitSplataZobowiazania() {
                                        return payment.getDt();
                                    }
                                    @Override public BigDecimal visitWplataNoty() {
                                        return clearings.payableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.N)
                                                .map( Clearing::getAmount)
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                    @Override public BigDecimal visitSplataNoty() {
                                        return clearings.receivableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.N)
                                                .map( Clearing::getAmount )
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }
                                    @Override public BigDecimal visitOplacenieUdzialow() {
                                        return clearings.payableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.D)
                                                .map( Clearing::getAmount)
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }

                                });
                    }

                    @Override public Optional<AccountDto> getAccount( TemplateLine line) {
                        return Optional.ofNullable(
                            switch( line.getAccount().getNumber().replaceAll("[^A-Z]+", "")) {
                                case "P"-> createAccount( line, EntityType.C);
                                case "W"-> createAccount( line, EntityType.S);
                                case "B"-> createAccount( line, PaymentRegisterType.B);
                                case "K"-> createAccount( line, PaymentRegisterType.C);
                                default -> AccountDto.create()
                                        .name( payment.getEntity().getName())
                                        .number( line.getAccount().getNumber());
                        });
                    }

                    private AccountDto createAccount( TemplateLine line, EntityType type) {
                        return type== payment.getEntity().getType()?
                                AccountDto.create()
                                        .name( payment.getEntity().getName())
                                        .parent( line.getAccount().getNumber())
                                        .number( line.getAccount().getNumber().replaceAll( "\\[[A-Z]\\]+",
                                                payment.getEntity().getAccountNumber())): null;
                    }

                    private AccountDto createAccount( TemplateLine line, PaymentRegisterType type) {
                        return type== payment.getRegister().getType()?
                                AccountDto.create()
                                        .name( payment.getRegister().getName())
                                        .parent( line.getAccount().getNumber())
                                        .number( line.getAccount().getNumber().replaceAll( "\\[[A-Z]\\]+",
                                                payment.getRegister().getAccountNumber())): null;
                    }
                }.build( template, payment.getIssueDate(), payment.getNumber(), payment.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( payment.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow( ()-> new NoSuchElementException( "No found template"));
    }

    @Override public TemplateType visitPaymentReceipt() {
        return TemplateType.PR;
    }

    @Override public TemplateType visitPaymentExpense() {
        return TemplateType.PE;
    }
}
