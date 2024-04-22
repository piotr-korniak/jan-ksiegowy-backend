package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;
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
import pl.janksiegowy.backend.finances.settlement.SettlementType;
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
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.statement.StatementDocument;
import pl.janksiegowy.backend.statement.StatementType.StatementTypeVisitor;
import pl.janksiegowy.backend.finances.payment.PaymentType.PaymentTypeVisitor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DecreeFactory implements SettlementType.SettlementTypeVisitor<TemplateType> {

    private final TemplateRepository templates;
    private final AccountingRegisterRepository registers;
    private final DecreeLineFactory line;
    private final PeriodFacade period;
    private final NumeratorFacade numerators;
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
            @Override public Decree visitBookingDecree() {
                return new BasicDecree();
            }
            @Override public Decree visitDocumentDecree() {
                return new DocumentDecree();
            }
        });
    }

    public DecreeDto to( FinancialSettlement settlement) {
        return templates.findByDocumentTypeAndDate( settlement.getFinancialType()
                .accept( new FinancialTypeVisitor<TemplateType>() {
            @Override public TemplateType visitChargeSettlement() {
                return TemplateType.CH;
            }
            @Override public TemplateType visitFeeSettlement() {
                return TemplateType.FE;
            }
            @Override public TemplateType visitNoteSettlement() {
                return null;
/*
                        settlement.getKind().accept( new SettlementKindVisitor<DocumentType>() {
                    @Override public DocumentType visitDebit() {
                        return DocumentType.NI;
                    }
                    @Override public DocumentType visitCredit() {
                        return DocumentType.NR;
                    }
                });*/
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
            }.build( template, settlement.getDate(), settlement.getNumber(), settlement.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( settlement.getDecree())
                        .map( decree-> decreeMap.setNumer( decreeMap.getNumber()))
                        .orElseGet(()-> decreeMap))
            .orElseThrow();
    }


    public DecreeDto to( Invoice invoice) {

        return templates.findByDocumentTypeAndDate( invoice.getType().accept( new InvoiceTypeVisitor<TemplateType>() {
                    @Override
                    public TemplateType visitSalesInvoice() {
                        return null;
                    }

                    @Override
                    public TemplateType visitPurchaseInvoice() {
                        return null;
                    }
                } ), invoice.getInvoiceDate())
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
                        case "P"-> account.name( invoice.getEntity().getName())
                                .number( account.getNumber().replaceAll( "\\[P\\]",
                                        invoice.getEntity().getAccountNumber()));
                        default -> account;
                    };
                }
            }.build( template, invoice.getDate(), invoice.getNumber(), invoice.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( invoice.getDecree())
                            .map( decree-> decreeMap.setNumer( decree.getNumber()))
                            .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    public DecreeDto to( Payment payment) {
        return templates.findByDocumentTypeAndDate( payment.getType().accept( new PaymentTypeVisitor<TemplateType>() {
                    @Override
                    public TemplateType visitPaymentReceipt() {
                        return null;
                    }

                    @Override
                    public TemplateType visitPaymentExpense() {
                        return null;
                    }
                } ), payment.getDate())
            .map( template-> new Builder() {
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
                                            //.filter( clearing-> clearing.getReverse( payment.getSettlement())
                                            //        .getType()== SettlementType.N)
                                            .map( Clearing::getAmount )
                                            .reduce( BigDecimal.ZERO, BigDecimal::add);
                                }
                                @Override public BigDecimal visitSplataNoty() {
                                    return clearings.receivableId( payment.getDocumentId()).stream()
                                            //.filter( clearing-> clearing.getReverse( payment.getSettlement())
                                            //        .getType()== SettlementType.N)
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
            }.build( template, payment.getDate(), payment.getNumber(), payment.getDocumentId()))
                .map( decreeMap-> Optional.ofNullable( payment.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
                .orElseThrow();
    }

    public DecreeDto to( StatementDocument statement ) {

        var pattern= PatternId.JPK_V7K_2_v1_0e;

        var jpkData= XmlConverter.unmarshal( Ewidencja_JPK_V7K_2_v1_0e.class, statement.getXml());


        return templates.findByDocumentTypeAndDate( statement.getType().accept( new StatementTypeVisitor<TemplateType>() {
                    @Override
                    public TemplateType visitVatStatement() {
                        return null;
                    }

                    @Override
                    public TemplateType visitJpkStatement() {
                        return null;
                    }

                    @Override
                    public TemplateType visitCitStatement() {
                        return null;
                    }

                    @Override
                    public TemplateType visitPitStatement() {
                        return null;
                    }

                    @Override
                    public TemplateType visitZusStatement() {
                        return null;
                    }
                } ), statement.getDate())
            .map( template-> new Builder() {
                @Override public BigDecimal getValue( TemplateLine line) {
                    return ((StatementTemplateLine)line).getFunction()
                            .accept( new StatementFunction.StatementFunctionVisitor<BigDecimal>() {

                        @Override public BigDecimal visitPodatekNalezny() {
                            return jpkData.getVatNalezny();
                        }

                        @Override public BigDecimal visitPodatekNaliczony() {
                            return jpkData.getVatNaliczony();
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
                            return jpkData.getZobowiazanie();
                        }
                    } );
                }

                @Override public AccountDto getAccount( AccountDto.Proxy account) {
                    return account;
                }
            }.build( template, statement.getPeriod().getEnd(), statement.getNumber(), statement.getStatementId()))
                .map( decreeMap-> Optional.ofNullable( statement.getDecree())
                        .map( decree-> decreeMap.setNumer( decree.getNumber()))
                        .orElseGet(()-> decreeMap))
            .orElseThrow();
    }

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

    @Override
    public TemplateType visitReceiptSettlement() {
        return null;
    }

    @Override
    public TemplateType visitInvoicePayable() {
        return null;
    }

    @Override
    public TemplateType visitInvoiceSettlemnt() {
        return null;
    }

    @Override
    public TemplateType visitPaymentSettlement() {
        return null;
    }

    @Override
    public TemplateType visitStatementSettlement() {
        return null;
    }

    @Override
    public TemplateType visitPayslipSettlement() {
        return null;
    }

    @Override
    public TemplateType visitChargeSettlement() {
        return null;
    }

    @Override
    public TemplateType visitFeeSettlement() {
        return null;
    }

    @Override
    public TemplateType visitNoteSettlement() {
        return null;
    }


    abstract static class Builder {

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
