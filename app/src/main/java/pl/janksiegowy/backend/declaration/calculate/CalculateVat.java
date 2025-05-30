package pl.janksiegowy.backend.declaration.calculate;

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
import pl.janksiegowy.backend.declaration.DeclarationElementCode;
import pl.janksiegowy.backend.declaration.StatementLine;
import pl.janksiegowy.backend.declaration.StatementRepository;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@AllArgsConstructor
public abstract class CalculateVat  {

    private final LocalDate dateApplicable= LocalDate.ofYearDay( 1970, 4);

    private final InvoiceLineQueryRepository lines;
    private final MetricRepository metrics;
    private final PeriodRepository periods;
    private final StatementRepository statements;

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

    @Component
    public static class CalculateVatQuarterly extends CalculateVat implements CalculateStrategy<Interpreter> {

        public CalculateVatQuarterly( final InvoiceLineQueryRepository lines,
                                      final MetricRepository metrics,
                                      final PeriodRepository periods,
                                      final StatementRepository statements) {
            super( lines, metrics, periods, statements);
        }

        @Override
        public Interpreter calculate( MonthPeriod period) {
            return super.calculate( period.getParent());
        }

        @Override
        public boolean isApplicable( TaxType taxType) {
            return taxType==TaxType.VQ;
        }

        @Override
        public LocalDate getDateApplicable() {
            return super.dateApplicable;
        }
    }
    
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
                                        statement.getElements()
                                                .getOrDefault( DeclarationElementCode.DO_PRZ, BigDecimal.ZERO));
                                    });
                        });
            //}
        });


        System.err.println( "Pokaż storno: "+ result.getVariable( "Z_Przeniesienia", BigDecimal.TEN));
        result.setVariable( "Z_Przeniesienia", result.getVariable( "Z_Przeniesienia", BigDecimal.ZERO));

        // Domestic sales divided by tax rate
        lines.sumSalesByKindAndPeriodGroupByRate( InvoiceRegisterKind.D, period.getBegin(), period.getEnd())
                .forEach( sum-> salesDomesticFunctions.get( sum.getTaxRate())
                        .accept( result, sum.getBase(), sum.getVat()));

        // Other purchase
        lines.sumPurchaseByKindAndItemTypeGroupByType( List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W),
                        period.getBegin(), period.getEnd())
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

        result.interpret( "Podatek", "[Razem_Nalezny]- [Z_Przeniesienia]- [Razem_Naliczony]");

        result.setVariable( "Do_Przeniesienia", result.getVariable( "Podatek").min( BigDecimal.ZERO).abs());
        result.setVariable( "Kwota_Zobowiazania", result.getVariable( "Podatek").max( BigDecimal.ZERO));

        return result;
    }

}
