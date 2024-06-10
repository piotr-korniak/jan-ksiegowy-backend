package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.PaymentFunction.PaymentFunctionVisitor;
import pl.janksiegowy.backend.accounting.template.PaymentTemplateLine;
import pl.janksiegowy.backend.accounting.template.TemplateLine;
import pl.janksiegowy.backend.accounting.template.TemplateRepository;
import pl.janksiegowy.backend.accounting.template.TemplateType;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.finances.settlement.SettlementType;

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

                    @Override public BigDecimal getValue( TemplateLine item) {
                        return Optional.of((PaymentTemplateLine) item)
                                .filter( line-> line.getRegisterType()== null
                                        || line.getRegisterType()== payment.getRegister().getType())
                                .map(line -> line.getFunction().accept( new PaymentFunctionVisitor<BigDecimal>() {
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

                                })).orElseGet(()-> BigDecimal.ZERO);
                    }
                    @Override public AccountDto getAccount( AccountDto.Proxy account) {
                        return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                            case "P"-> account.name( payment.getEntity().getName())
                                    .number( account.getNumber().replaceAll( "\\[P\\]",
                                            payment.getEntity().getAccountNumber()));
                            case "B"-> account.name( payment.getRegister().getName())
                                    .number( account.getNumber().replaceAll( "\\[B\\]",
                                            payment.getRegister().getAccountNumber()));
                            case "K"-> account.name( payment.getRegister().getName())
                                    .number( account.getNumber().replaceAll( "\\[K\\]",
                                            payment.getRegister().getAccountNumber()));
                            default -> account;
                        };
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
