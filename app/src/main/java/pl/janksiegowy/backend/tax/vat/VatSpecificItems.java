package pl.janksiegowy.backend.tax.vat;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.*;
import pl.janksiegowy.backend.period.Period.PeriodVisitor;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.statement.StatementItemCode;
import pl.janksiegowy.backend.statement.StatementLine;
import pl.janksiegowy.backend.statement.StatementRepository;
import pl.janksiegowy.backend.tax.SpecificItems;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class VatSpecificItems implements SpecificItems<Interpreter> {

    private final LocalDate dateApplicable= LocalDate.of( 1970, 1, 4);
    private final PeriodVisitor<List<JpaInvoiceSumDto>> domesticSales= new PeriodVisitor<List<JpaInvoiceSumDto>>() {
        @Override public List<JpaInvoiceSumDto> visit( AnnualPeriod annualPeriod) {
            throw new IllegalArgumentException( "Unsupported period type: Annual");
        }
        @Override public List<JpaInvoiceSumDto> visit( QuarterPeriod quarterPeriod) {
            return lines.sumSalesByKindAndPeriodGroupByRate(
                    InvoiceRegisterKind.D, quarterPeriod);
        }
        @Override public List<JpaInvoiceSumDto> visit( MonthPeriod monthPeriod) {
            return lines.sumSalesByKindAndPeriodGroupByRate(
                    InvoiceRegisterKind.D, monthPeriod);
        }
    };

    private final PeriodVisitor<List<JpaInvoiceSumDto>> otherPurchase= new PeriodVisitor<List<JpaInvoiceSumDto>>() {
        @Override public List<JpaInvoiceSumDto> visit( AnnualPeriod annualPeriod) {
            throw new IllegalArgumentException( "Unsupported period type: Annual");
        }
        @Override public List<JpaInvoiceSumDto> visit( QuarterPeriod quarterPeriod) {
            return lines.sumPurchaseByKindAndItemTypeGroupByType(
                    List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W), quarterPeriod);
        }
        @Override public List<JpaInvoiceSumDto> visit( MonthPeriod monthPeriod) {
            return lines.sumPurchaseByKindAndItemTypeGroupByType(
                    List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W), monthPeriod);
        }
    };

    private final PeriodVisitor<List<JpaInvoiceSumDto>> purchase= new PeriodVisitor<List<JpaInvoiceSumDto>>() {
        @Override public List<JpaInvoiceSumDto> visit( AnnualPeriod annualPeriod) {
            throw new IllegalArgumentException( "Unsupported period type: Annual");
        }
        @Override public List<JpaInvoiceSumDto> visit( QuarterPeriod quarterPeriod) {
            return lines.sumPurchaseByTypeAndPeriodGroupByType( quarterPeriod);
        }
        @Override public List<JpaInvoiceSumDto> visit( MonthPeriod monthPeriod) {
            return lines.sumPurchaseByTypeAndPeriodGroupByType( monthPeriod);
        }
    };

    private final InvoiceLineQueryRepository lines;
    private final MetricRepository metrics;
    private final PeriodRepository periods;
    private final StatementRepository statements;
    private final Map<TaxRate, TriConsumer<Interpreter, BigDecimal, BigDecimal>>
            salesDomesticFunctions= Map.of(
            TaxRate.S1, (account, base, vat)-> {
                account.add( "Sprzedaz_Netto_S1", base);
                account.interpret( "Sprzedaz_Netto_S1", "[Sprzedaz_Netto_S1]@ [JEDEN]");
                account.add( "Sprzedaz_Vat_S1", vat);
                account.interpret( "Sprzedaz_Vat_S1", "[Sprzedaz_Vat_S1]@ [JEDEN]");
                account.add( "Korekta_Naleznego",
                        vat.subtract( account.getVariable( "Sprzedaz_Vat_S1")));
            },
            TaxRate.S2, (account, base, vat)-> {
                account.add( "Sprzedaz_Netto_S2", base);
                account.interpret( "Sprzedaz_Netto_S2", "[Sprzedaz_Netto_S2]@ [JEDEN]");
                account.add( "Sprzedaz_Vat_S2", vat);
                account.interpret( "Sprzedaz_Vat_S2", "[Sprzedaz_Vat_S2]@ [JEDEN]");
                account.add( "Korekta_Naleznego",
                        vat.subtract( account.getVariable( "Sprzedaz_Vat_S2" )));
            });

    @Override public boolean isApplicable(TaxType taxType) {
        return taxType==TaxType.VM;
    }

    @Override
    public LocalDate getDateApplicable() {
        return dateApplicable;
    }

    @Override
    public Interpreter calculate( Period period) {
        final Interpreter result= (new Interpreter()).setVariable("JEDEN", BigDecimal.ONE);

        metrics.findByDate( period.getBegin().minusDays( 1)).ifPresent( metric-> {
            if( metric.isVatQuarterly()){

            }
            //if( metric.isVatMonthly()== VAT.Yes){

                periods.findMonthByDate( period.getBegin().minusDays(1))
                        .ifPresent( monthPeriod -> {
                            statements.findFirstByPatternLikeAndPeriodOrderByNoDesc( "VAT%", monthPeriod)
                                    .ifPresent( statement -> {
                                        result.add( "Z_Przeniesienia",
                                        statement.getLines().stream()
                                                .collect( Collectors.toMap( StatementLine::getItemCode, StatementLine::getAmount))
                                                .getOrDefault( StatementItemCode.DO_PRZ, BigDecimal.ZERO));
                                    });
                        });
            //}
        });


        System.err.println( "Pokaż storno: "+ result.getVariable( "Z_Przeniesienia", BigDecimal.TEN));
        result.setVariable( "Z_Przeniesienia", result.getVariable( "Z_Przeniesienia", BigDecimal.ZERO));

        // Domestic sales divided by tax rate
        period.accept( domesticSales)
                .forEach( sum-> salesDomesticFunctions.get( sum.getTaxRate())
                        .accept( result, sum.getBase(), sum.getVat()));

        // Other purchase
        period.accept( otherPurchase)
                .forEach( sum-> { switch (sum.getPurchaseKind()) {
                    case W -> {
                        result.add("Import_Uslug_Netto", sum.getBase());
                        result.add("Import_Uslug_Vat", sum.getVat());
                    }
                }});

        // must be because there is more than one grouping criterion
        var korekta = BigDecimal.ZERO;
        if (result.isVariable("Import_Uslug_Vat")) {
            korekta = result.getVariable("Import_Uslug_Vat");
            result.interpret("Import_Uslug_Vat", "[Import_Uslug_Vat]@ [JEDEN]");
            result.add("Korekta_Naleznego",
                    korekta.subtract(result.getVariable("Import_Uslug_Vat")));
        }

        if (result.isVariable("Import_Uslug_Netto"))
            result.interpret("Import_Uslug_Netto", "[Import_Uslug_Netto]@ [JEDEN]");

        result.sum("Razem", "Sprzedaz_Netto_S1", "Import_Uslug_Netto");
        result.sum("Razem_Nalezny",  "Sprzedaz_Vat_S1", "Import_Uslug_Vat");

        // Purchase
        period.accept( purchase)
                .forEach( sum-> {
                    switch (sum.getItemType()) {
                        case A -> {
                            result.add("Zakupy_Trwale_Netto", sum.getBase());
                            result.add("Zakupy_Trwale_Vat", sum.getVat());
                            result.interpret("Zakupy_Trwale_Vat", "[Zakupy_Trwale_Vat]@ [JEDEN]");
                            result.add("Korekta_Naliczonego",
                                    sum.getVat().subtract(result.getVariable("Zakupy_Trwale_Vat")));
                        }
                        case S, M, P -> {
                            result.add("Zakupy_Pozostale_Netto", sum.getBase());
                            result.add("Zakupy_Pozostale_Vat", sum.getVat());
                        }
                    }
                });

        if( result.isVariable( "Zakupy_Pozostale_Vat")) {
            korekta= result.getVariable( "Zakupy_Pozostale_Vat");
            result.interpret( "Zakupy_Pozostale_Vat", "[Zakupy_Pozostale_Vat]@ [JEDEN]" );
            result.add( "Korekta_Naliczonego",
                    korekta.subtract( result.getVariable( "Zakupy_Pozostale_Vat")));
        }

        result.sum( "Razem_Naliczony", "Zakupy_Trwale_Vat", "Zakupy_Pozostale_Vat");

        result.interpret( "Podatek", "[Razem_Nalezny]- [Razem_Naliczony]");

        System.err.println( "Razem należny: "+ result.getVariable( "Razem_Nalezny"));

        result.setVariable( "Kwota_Zobowiazania", result.getVariable( "Podatek"));
        if( result.getVariable( "Podatek").signum()<0)
            result.setVariable( "Do_Przeniesienia", result.getVariable( "Podatek").abs());

        return result;
    }

}
