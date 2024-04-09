package pl.janksiegowy.backend.statement;

import org.apache.logging.log4j.util.TriConsumer;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public abstract class Factory_JPK_V7 {
    protected Period period;
    protected Map<UUID, List<JpaInvoiceSumDto>> salesInvoice;
    protected Map<UUID, List<JpaInvoiceSumDto>> purchaseInvoice;
    protected Interpreter account;

    private final Map<TaxRate, TriConsumer<Interpreter, BigDecimal, BigDecimal>>
            salesDomesticFunctions= Map.of(
                TaxRate.S1, (account, base, vat)-> {
                    account.add( "Sprzedaz_Netto_S1", base);
                    account.interpret( "Sprzedaz_Netto_S1", "[Sprzedaz_Netto_S1]@ [JEDEN]");
                    account.add( "Sprzedaz_Vat_S1", vat);
                    account.interpret( "Sprzedaz_Vat_S1", "[Sprzedaz_Vat_S1]@ [JEDEN]");
                },
                TaxRate.S2, (account, base, vat)-> {
                    account.add( "Sprzedaz_Netto_S2", base);
                    account.interpret( "Sprzedaz_Netto_S2", "[Sprzedaz_Netto_S2]@ [JEDEN]");
                    account.add( "Sprzedaz_Vat_S2", vat);
                    account.interpret( "Sprzedaz_Vat_S2", "[Sprzedaz_Vat_S2]@ [JEDEN]");
                });

    public static Factory_JPK_V7 create( MonthPeriod period, InvoiceLineQueryRepository lines) {
        return getPatternId( Util.min( LocalDate.now(), period.getEnd().plusDays( 25)))
                .map( patternId-> patternId.accept( new PatternId.PatternJpkVisitor<Factory_JPK_V7>() {
                    @Override
                    public Factory_JPK_V7 visit_JPK_V7K_2_v1_0e() {
                        return ((Factory_JPK_V7) new Factory_JPK_V7K_2_v1_0e()).prepare( period, lines);
                    }
                }))
                .orElseThrow();
    }

    protected abstract Statement_JPK_V7 prepare( Metric metric );

    private Factory_JPK_V7 prepare( MonthPeriod period, InvoiceLineQueryRepository lines) {
        this.period= period;
        this.account = (new Interpreter())
                .setVariable( "JEDEN", BigDecimal.ONE)
                .setVariable( "S21", BigDecimal.ZERO)
                .setVariable( "S22", BigDecimal.ZERO);

        this.salesInvoice= lines.findByKindAndPeriodIdGroupByRate(
                        List.of( InvoiceRegisterKind.D),
                        List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W),
                        period.getId())
                .stream()
                .collect( Collectors.groupingBy( JpaInvoiceSumDto::getInvoiceId,
                        LinkedHashMap::new, Collectors.toList()));

        this.purchaseInvoice= lines.findByKindAndPeriodGroupByType( period.getId())
                .stream()
                .collect( Collectors.groupingBy( JpaInvoiceSumDto::getInvoiceId,
                        LinkedHashMap::new, Collectors.toList()));


        // Domestic sales divided by tax rate
        lines.sumSalesByKindAndPeriodGroupByRate( InvoiceRegisterKind.D, period.getParent())
                .forEach( sum-> salesDomesticFunctions.get( sum.getTaxRate())
                        .accept( account, sum.getBase(), sum.getVat()));

        // Other purchase
        lines.sumPurchaseByKindAndItemTypeGroupByType(
                        List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W), period.getParent())
                .forEach( sum-> {
                    switch (sum.getPurchaseKind()) {
                        case W -> {
                            account.add( "Import_Uslug_Netto", sum.getBase() );
                            account.add( "Import_Uslug_Vat", sum.getVat() );}
                    }
                });
        // must be because there is more than one grouping criterion
        if( account.isVariable( "Import_Uslug_Vat"))
            account.interpret( "Import_Uslug_Vat", "[Import_Uslug_Vat]@ [JEDEN]");
        if( account.isVariable( "Import_Uslug_Netto"))
            account.interpret( "Import_Uslug_Netto", "[Import_Uslug_Netto]@ [JEDEN]");


        account.sum( "Razem", "Sprzedaz_Netto_S1", "Import_Netto_Vat");
        account.sum( "Razem_Nalezny", "Sprzedaz_Vat_S1", "Import_Uslug_Vat");


        lines.sumPurchaseByTypeAndPeriodGroupByType( period.getParent())
                .forEach( sum-> {
                    switch ( sum.getItemType()) {
                        case A -> {
                            account.add( "Zakupy_Trwale_Netto", sum.getBase());
                            account.add( "Zakupy_Trwale_Vat", sum.getVat());
                            account.interpret( "Zakupy_Trwale_Vat",
                                    "[Zakupy_Trwale_Vat]@ [JEDEN]");}
                        case S, M, P -> {
                            account.add( "Zakupy_Pozostale_Netto", sum.getBase());
                            account.add( "Zakupy_Pozostale_Vat", sum.getVat());
                            account.interpret( "Zakupy_Pozostale_Vat",
                                    "[Zakupy_Pozostale_Vat]@ [JEDEN]");}
                    }
                });
        account.sum( "Razem_Naliczony", "Zakupy_Trwale_Vat", "Zakupy_Pozostale_Vat");

        account.interpret( "Podatek", "[Razem_Nalezny]- [Razem_Naliczony]");
        if( account.getVariable( "Podatek").signum()>0)
            account.setVariable( "Kwota_Zobowiazania", account.getVariable( "Podatek"));

        return this;
    }

    private static Optional<PatternId> getPatternId( LocalDate date) {
        return Optional.of( PatternId.JPK_V7K_2_v1_0e);
    }

}
