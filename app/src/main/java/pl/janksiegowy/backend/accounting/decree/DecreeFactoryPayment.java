package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.accounting.template.PaymentFunction.PaymentFunctionVisitor;
import pl.janksiegowy.backend.declaration.DeclarationType;
import pl.janksiegowy.backend.finances.clearing.ClearingQueryRepository;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.finances.payment.dto.ClearingDto;
import pl.janksiegowy.backend.finances.settlement.SettlementKind.SettlementKindVisitor;
import pl.janksiegowy.backend.salary.PayslipRepository;
import pl.janksiegowy.backend.salary.contract.ContractType;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandEntityAccount;
import static pl.janksiegowy.backend.accounting.decree.DecreeFacadeTools.expandPaymentRegisterAccount;

@AllArgsConstructor
public class DecreeFactoryPayment implements PaymentTypeVisitor<TemplateType>, SettlementKindVisitor<BigDecimal> {

    private final TemplateRepository templates;
    protected final ClearingRepository clearings;
    private final ClearingQueryRepository clearingQuery;
    private final PayslipRepository payslipRepository;

    public DecreeDto to( Payment payment) {
        var documentType= payment.getType().accept( this);
        payment.getEntity().getType();


        return templates.findByDocumentTypeAndDateAndPaymentType( documentType, payment.getIssueDate(), payment.getEntity().getType())
                .or( ()->templates.findByDocumentTypeAndDate( documentType, payment.getIssueDate()) )
                .map( template-> new DecreeFactory.Builder() {

                    @Override public BigDecimal getValue( TemplateLine line) {
                        return ((PaymentTemplateLine)line).getFunction()
                                .accept( new PaymentFunctionVisitor<BigDecimal>() {
                                    @Override public BigDecimal visitWplataNaleznosci() {
                                        return payment.getCt();
                                    }
                                    @Override public BigDecimal visitSplataZobowiazania() {
                                        System.err.println( "SplataZobowiazania: "+ payment.getDt());

                                        var dupa= payment.getSettlementKind().accept(
                                                                    new SettlementKindVisitor<BigDecimal>() {
                                            @Override public BigDecimal visitDebit() {
                                                return payment.getDt();
                                            }

                                            @Override public BigDecimal visitCredit() {
                                                return null;
                                            }
                                        });
                                        System.err.println( "SplataZobowiazania: "+ payment.getDt());
                                        System.err.println( "SplataZobowiazaniaII: "+ dupa);

                                        return payment.getDt();
                                    }
                                    @Override public BigDecimal visitWplataNoty() {
                                        return null;
                                        /*
                                        return clearings.payableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.N)
                                                .map( Clearing::getAmount)
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);*/
                                    }
                                    @Override public BigDecimal visitSplataNoty() {
                                        return BigDecimal.ZERO;
                                        /*
                                        return clearings.receivableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.N
                                                        && EntityType.R!= payment.getEntity().getType())
                                                .map( Clearing::getAmount )
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);*/
                                    }
                                    @Override public BigDecimal visitSplataNKUP() {
                                        return BigDecimal.ZERO;
                                        /*
                                        return clearings.receivableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.N
                                                        && EntityType.R== payment.getEntity().getType())
                                                .map( Clearing::getAmount )
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);*/
                                    }

                                    @Override public BigDecimal visitWartoscRozrachowania() {
                                        var result = Optional.ofNullable( line.getSettlementType())
                                                .map(settlementType-> clearingQuery.findByReverse(
                                                                payment.getDocumentId(),
                                                                payment.getSettlementKind(),
                                                                settlementType)
                                                        .stream()
                                                        .map( ClearingDto::getAmount)
                                                        .reduce( BigDecimal.ZERO, BigDecimal::add))
                                                .orElseGet( payment::getAmount);
                                        return "-".equals( line.getParameter()) ? result.negate() : result;
                                    }

                                    @Override public BigDecimal visitWartoscRozrachowaniaPublicznego() {
                                        try {
                                            return clearingQuery.findByPayable( payment.getDocumentId(),
                                                            DeclarationType.valueOf( line.getParameter()))
                                                    .stream()
                                                    .map( ClearingDto::getAmount)
                                                    .reduce( BigDecimal.ZERO, BigDecimal::add);
                                        } catch (IllegalArgumentException | NullPointerException e) {
                                            return BigDecimal.ZERO;
                                        }
                                    }

                                    @Override public BigDecimal visitWartoscRozrachowaniaPoTerminie() {
                                        try {
                                            return clearingQuery.findByPayable( payment.getDocumentId(),
                                                            DeclarationType.valueOf( line.getParameter()))
                                                    .stream()
                                                    .map( ClearingDto::getAmount)
                                                    .reduce( BigDecimal.ZERO, BigDecimal::add);
                                        } catch (IllegalArgumentException | NullPointerException e) {
                                            return BigDecimal.ZERO;
                                        }
                                    }

                                    @Override public BigDecimal visitWynagrodzenieNetto() {
                                        if( line.getSettlementType()== null)
                                            return payment.getAmount();
                                        return clearingQuery.findByReverse( payment.getDocumentId(),
                                                        payment.getSettlementKind(),
                                                        line.getSettlementType())
                                                .stream()
                                                .filter( DecreeFactoryPayment.this::isIncluded)
                                                .map( ClearingDto::getAmount)
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                    }

                                    @Override public BigDecimal visitSplataVat() {
/*
                                        var temp= clearings.receivableId( payment.getDocumentId()).stream()
                                                .filter( clearing-> clearing.getReverse( payment.getDocumentId())
                                                        .getType()== SettlementType.V)
                                                .map( Clearing::getAmount)
                                                .reduce( BigDecimal.ZERO, BigDecimal::add);
                                        System.err.println( "Spłata VAT: "+ temp);
                                        return temp;*/
                                        return BigDecimal.ZERO;
                                    }

                                });
                    }

                    @Override public Optional<AccountDto> getAccount( TemplateLine line) {
                        var account= line.getAccount();

                        if( account.getNumber().matches(".*\\[[CERS]].*"))
                            return expandEntityAccount( account.getNumber(), payment.getEntity());

                        if( account.getNumber().matches(".*\\[[AD]].*"))
                            return expandPaymentRegisterAccount( account.getNumber(), payment.getRegister());

                        return Optional.of( AccountDto.create()
                                .name( account.getName())
                                .number( account.getNumber()));

                    }
                }.build( template, payment.getIssueDate(), payment.getNumber(), payment.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( payment.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow( ()-> new NoSuchElementException( "No found template"));
    }

    private boolean isIncluded( ClearingDto clearing) {
        return !payslipRepository.findById( clearing.getPayableId())
                .map( payslip-> ContractType.E== payslip.getContract().getType()&&
                        !clearing.getDate().isAfter( payslip.getDueDate()))
                .orElseGet(()-> false);
    }

    @Override public TemplateType visitPaymentReceipt() {
        return TemplateType.PR;
    }

    @Override public TemplateType visitPaymentExpense() {
        return TemplateType.PE;
    }

    @Override
    public BigDecimal visitDebit() {
        return null;
    }

    @Override
    public BigDecimal visitCredit() {
        return null;
    }
}
