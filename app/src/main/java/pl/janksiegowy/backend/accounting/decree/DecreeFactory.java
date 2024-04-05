package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.dto.AccountDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.accounting.decree.DecreeType.DecreeTypeVisitor;
import pl.janksiegowy.backend.accounting.template.*;
import pl.janksiegowy.backend.accounting.template.PaymentFunction.PaymentFunctionVisitor;
import pl.janksiegowy.backend.accounting.template.InvoiceFunction.InvoiceFunctionVisitor;
import pl.janksiegowy.backend.accounting.template.FinanceFunction.NoteFunctionVisitor;
import pl.janksiegowy.backend.finances.clearing.Clearing;
import pl.janksiegowy.backend.finances.clearing.ClearingRepository;
import pl.janksiegowy.backend.finances.payment.Payment;
import pl.janksiegowy.backend.finances.settlement.*;
import pl.janksiegowy.backend.finances.settlement.FinancialType.FinancialTypeVisitor;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceType.InvoiceTypeVisitor;
import pl.janksiegowy.backend.invoice_line.InvoiceLine;
import pl.janksiegowy.backend.item.ItemType;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.register.accounting.AccountingRegisterRepository;
import pl.janksiegowy.backend.register.dto.RegisterDto;
import pl.janksiegowy.backend.salary.Payslip;
import pl.janksiegowy.backend.shared.numerator.NumeratorCode;
import pl.janksiegowy.backend.shared.numerator.NumeratorFacade;
import pl.janksiegowy.backend.shared.pattern.PatternFactory;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.Statement;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;
import pl.janksiegowy.backend.finances.settlement.SettlementKind.SettlementKindVisitor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DecreeFactory implements InvoiceTypeVisitor<DocumentType>,
                                      PaymentTypeVisitor<DocumentType>,
                                      StatementTypeVisitor<DocumentType>,
                                      FinancialTypeVisitor<DocumentType> {

    private final TemplateRepository templates;
    private final AccountingRegisterRepository registers;
    private final DecreeLineFactory line;
    private final PeriodFacade period;
    private final NumeratorFacade numerators;
    private final PatternFactory patterns;
    private final ClearingRepository clearings;

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
            @Override public Decree visitBasicDecree() {
                return new BasicDecree();
            }
            @Override public Decree visitSettlementDecree() {
                return new SettlementDecree();
            }
        });
    }

    public DecreeDto to( FinancialSettlement settlement) {
        return templates.findByDocumentTypeAndDate( settlement.getFinancialType()
                .accept( new FinancialTypeVisitor<DocumentType>() {
            @Override public DocumentType visitChargeSettlement() {
                return DocumentType.CH;
            }
            @Override public DocumentType visitFeeSettlement() {
                return DocumentType.FE;
            }
            @Override public DocumentType visitNoteSettlement() {
                return settlement.getKind().accept( new SettlementKindVisitor<DocumentType>() {
                    @Override public DocumentType visitDebit() {
                        return DocumentType.NI;
                    }
                    @Override public DocumentType visitCredit() {
                        return DocumentType.NR;
                    }
                });
            }
        }), settlement.getDate())
            .map( template -> new Builder() {
                @Override public BigDecimal getValue( TemplateLine line ) {
                    return ((FinanceTemplateLine)line).getFunction().accept( new NoteFunctionVisitor<BigDecimal>() {
                        @Override public BigDecimal visitWartoscZobowiazania() {
                            return settlement.getCt();
                        }
                        @Override public BigDecimal visitWartoscNaleznosci() {
                            return settlement.getDt();
                        }
                    });
                }
                @Override public AccountDto getAccount( AccountDto.Proxy account) {
                    return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                        case "P"-> account.name( settlement.getEntity().getName())
                                .number( account.getNumber().replaceAll( "\\[P\\]",
                                        settlement.getEntity().getAccountNumber()));
                        default -> account;
                    };
                }
            }.build( template, settlement.getDate(), settlement.getNumber(), settlement.getSettlementId()))
                .map( decreeMap-> Optional.ofNullable( settlement.getDecree())
                        .map( decree-> decreeMap.setNumer( decreeMap.getNumber()))
                        .orElseGet(()-> decreeMap))
            .orElseThrow();
    }


    public DecreeDto to( Invoice invoice) {

        return templates.findByDocumentTypeAndDate( invoice.getType().accept( this), invoice.getInvoiceDate())
            .map( template-> new Builder() {
                @Override public BigDecimal getValue( TemplateLine line) {
                    return ((InvoiceTemplateLine)line).getFunction()
                            .accept( new InvoiceFunctionVisitor<BigDecimal>() {
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
                    });
                }
                @Override public AccountDto getAccount( AccountDto.Proxy account) {
                    return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                        case "P"-> account.name( invoice.getSettlement().getEntity().getName())
                                .number( account.getNumber().replaceAll( "\\[P\\]",
                                        invoice.getSettlement().getEntity().getAccountNumber()));
                        default -> account;
                    };
                }
            }.build( template, invoice.getIssueDate(), invoice.getNumber(), invoice.getInvoiceId()))
                .map( decreeMap-> Optional.ofNullable( invoice.getSettlement().getDecree())
                            .map( decree-> decreeMap.setNumer( decree.getNumber()))
                            .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    public DecreeDto to( Payment payment) {
        return templates.findByDocumentTypeAndDate( payment.getType().accept( this), payment.getDate())
            .map( template-> new Builder() {
                @Override public BigDecimal getValue( TemplateLine item) {
                    return Optional.of((PaymentTemplateLine) item)
                            .filter( line-> line.getRegisterType()== null
                                        || line.getRegisterType()== payment.getRegister().getType())
                            .map(line -> line.getFunction().accept( new PaymentFunctionVisitor<BigDecimal>() {
                                @Override public BigDecimal visitWplataNaleznosci() {
                                    return payment.getSettlement().getCt();
                                }
                                @Override public BigDecimal visitSplataZobowiazania() {
                                    return payment.getSettlement().getDt();
                                }
                                @Override public BigDecimal visitWplataNoty() {
                                    return clearings.payable( payment.getSettlement()).stream()
                                            .filter( clearing-> clearing.getReverse( payment.getSettlement())
                                                    .getType()== SettlementType.N)
                                            .map( Clearing::getAmount )
                                            .reduce( BigDecimal.ZERO, BigDecimal::add);
                                }
                                @Override public BigDecimal visitSplataNoty() {
                                    return clearings.receivable( payment.getSettlement()).stream()
                                            .filter( clearing-> clearing.getReverse( payment.getSettlement())
                                                    .getType()== SettlementType.N)
                                            .map( Clearing::getAmount )
                                            .reduce( BigDecimal.ZERO, BigDecimal::add);
                                }

                            })).orElseGet(()-> BigDecimal.ZERO);
                }
                @Override public AccountDto getAccount( AccountDto.Proxy account) {
                    return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                        case "P"-> account.name( payment.getSettlement().getEntity().getName())
                                            .number( account.getNumber().replaceAll( "\\[P\\]",
                                                    payment.getSettlement().getEntity().getAccountNumber()));
                        case "B"-> account.name( payment.getRegister().getName())
                                            .number( account.getNumber().replaceAll( "\\[B\\]",
                                                    payment.getRegister().getAccountNumber()));
                        case "K"-> account.name( payment.getRegister().getName())
                                            .number( account.getNumber().replaceAll( "\\[K\\]",
                                                    payment.getRegister().getAccountNumber()));
                        default -> account;
                    };
                }
            }.build( template, payment.getDate(), payment.getNumber(), payment.getPaymentId()))
                .map( decreeMap-> Optional.ofNullable( payment.getSettlement().getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    public DecreeDto to( Statement statement ) {

        var pattern= PatternId.JPK_V7K_2_v1_0e;

        var jpkData= XmlConverter.unmarshal( pattern.accept( patterns).getClass(), statement.getXml());


        return templates.findByDocumentTypeAndDate( statement.getType().accept( this), statement.getDate())
            .map( template-> new Builder() {
                @Override public BigDecimal getValue( TemplateLine line) {
                    return ((StatementTemplateLine)line).getFunction()
                            .accept( new StatementFunction.StatementFunctionVisitor<BigDecimal>() {

                        @Override public BigDecimal visitPodatekNalezny() {
                            return new BigDecimal( jpkData.getVatNalezny());
                        }

                        @Override public BigDecimal visitPodatekNaliczony() {
                            return new BigDecimal( jpkData.getVatNaliczony());
                        }

                        @Override public BigDecimal visitKorektaNaleznegoPlus() {
                            return statement.getValue_1().signum()>0? statement.getValue_1(): BigDecimal.ZERO;
                        }

                        @Override public BigDecimal visitKorektaNaleznegoMinus() {
                            return statement.getValue_1().signum()<0? statement.getValue_1().negate(): BigDecimal.ZERO;
                        }

                        @Override public BigDecimal visitKorektaNaliczonegoPlus() {
                            return statement.getValue_2().signum()>0? statement.getValue_2(): BigDecimal.ZERO;
                        }

                        @Override public BigDecimal visitKorektaNaliczonegoMinus() {
                            return statement.getValue_2().signum()<0? statement.getValue_2().negate(): BigDecimal.ZERO;
                        }

                        @Override public BigDecimal visitZobowiazanie() {
                            return new BigDecimal( jpkData.getP51());
                        }
                    } );
                }

                @Override public AccountDto getAccount( AccountDto.Proxy account) {
                    return account;
                }
            }.build( template, statement.getPeriod().getEnd(), statement.getNumber(), statement.getStatementId()))
/*                .map( decreeMap-> Optional.ofNullable( statement.getSettlement().getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))*/
            .orElseThrow();
    }

    public DecreeDto to( Payslip payslip) {

        return templates.findByDocumentTypeAndDate( DocumentType.EP, payslip.getDate())
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
                                return payslip.getSettlement().getCt();
                            }
                        } );
                    }

                    @Override public AccountDto getAccount( AccountDto.Proxy account) {
                        return switch( account.getNumber().replaceAll("[^A-Z]+", "")){
                            case "P"-> account.name( payslip.getSettlement().getEntity().getName())
                                    .number( account.getNumber().replaceAll( "\\[P\\]",
                                            payslip.getSettlement().getEntity().getAccountNumber()));
                            default -> account;
                        };
                    }
                }.build( template, payslip.getDate(), payslip.getSettlement().getNumber(), payslip.getPayslipId()))
                .map( decreeMap-> Optional.ofNullable( payslip.getSettlement().getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();

    }


    abstract static class Builder {

        public abstract BigDecimal getValue( TemplateLine line);
        abstract public AccountDto getAccount( AccountDto.Proxy accountNumber);

        public DecreeMap build( Template template, LocalDate date, String document, UUID decreeId) {
            var decree= new DecreeMap( DecreeDto.create()
                    .type( DecreeType.S)
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

    @Override public DocumentType visitSalesInvoice() {
        return DocumentType.IS;
    }
    @Override public DocumentType visitPurchaseInvoice() {
        return DocumentType.IP;
    }

    @Override public DocumentType visitPaymentReceipt() {
        return DocumentType.PR;
    }
    @Override public DocumentType visitPaymentSpend() {
        return DocumentType.PS;
    }

    @Override public DocumentType visitVatStatement() {
        return DocumentType.SV;
    }
    @Override public DocumentType visitCitStatement() {
        return DocumentType.SC;
    }
    @Override public DocumentType visitPitStatement() {
        return DocumentType.SP;
    }
    @Override public DocumentType visitZusStatement() {
        return DocumentType.SN;
    }
    @Override public DocumentType visitChargeSettlement() {
        return null;
    }
    @Override public DocumentType visitFeeSettlement() {
        return null;
    }
    @Override public DocumentType visitNoteSettlement() {
        return null;
    }
}
