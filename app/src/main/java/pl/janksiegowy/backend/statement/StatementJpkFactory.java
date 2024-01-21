package pl.janksiegowy.backend.statement;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e.Deklaracja_VAT_7K_16_1_0e;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e.Deklaracja_VAT_7K_16_1_0e.NaglowekDekalaracji;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e.Deklaracja_VAT_7K_16_1_0e.PozycjeSzczegolowe;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e.Ewidencja;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e.Ewidencja.SprzedazWiersz;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_v1_0e.Ewidencja.ZakupWiersz;

import pl.janksiegowy.backend.financial.TaxRate;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.item.ItemType;
import pl.janksiegowy.backend.period.MonthPeriod;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StatementJpkFactory implements JpkVat.Jpk_Vat7kVisitor<Object> {

    private final InvoiceLineQueryRepository lines;
    private final MonthPeriod period;
    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "yyyy-MM-dd");

    private final Map<TaxRate, TriConsumer<PozycjeSzczegolowe, BigDecimal, BigDecimal>>
            salesDomesticFunctions= Map.ofEntries(
                    Map.entry( TaxRate.S1, PozycjeSzczegolowe::addP1920 ),
                    Map.entry( TaxRate.S2, PozycjeSzczegolowe::addP2122 ));
    private final Map<InvoiceRegisterKind,
            Map<ItemType, TriConsumer<PozycjeSzczegolowe, BigDecimal, BigDecimal>>>
            purchaseOtherFunctions= Map.ofEntries(
                    Map.entry( InvoiceRegisterKind.W, Map.of(
                            ItemType.S, PozycjeSzczegolowe::addP2728 )));
    private final Map<ItemType, TriConsumer<PozycjeSzczegolowe, BigDecimal, BigDecimal>>
            purchaseFunctions= Map.of(
                    ItemType.A, PozycjeSzczegolowe::addP4041,
                    ItemType.M, PozycjeSzczegolowe::addP4243,
                    ItemType.P, PozycjeSzczegolowe::addP4243,
                    ItemType.S, PozycjeSzczegolowe::addP4243 );
    private final Map<TaxRate, TriConsumer<SprzedazWiersz, BigDecimal, BigDecimal>>
            salesDomesticRowFunctions = Map.of( TaxRate.S1, SprzedazWiersz::setK1920);
    private final Map<InvoiceRegisterKind,
            Map<ItemType, TriConsumer<SprzedazWiersz, BigDecimal, BigDecimal>>>
            purchaseOtherRowFunctions= Map.ofEntries(
                    Map.entry( InvoiceRegisterKind.W, Map.of(
                            ItemType.S, SprzedazWiersz::setK2728)));

    private final Map<ItemType, TriConsumer<ZakupWiersz, BigDecimal, BigDecimal>>
            purchaseRowFunctions= Map.of(
                    ItemType.A, ZakupWiersz::addK4041,
                    ItemType.M, ZakupWiersz::addK4243,
                    ItemType.P, ZakupWiersz::addK4243,
                    ItemType.S, ZakupWiersz::addK4243);

    @Override public Object visitJPK_V7K_2_v1_0e() {
        var sprzedazCtrl=  Ewidencja.SprzedazCtrl.create();
        var zakupCtrl= Ewidencja.ZakupCtrl.create();

        return (isLastMonthOfQuarter( period.getBegin())
                ? Ewidencja_JPK_V7K_2_v1_0e.create()
                    .deklaracja( Deklaracja_VAT_7K_16_1_0e.create()
                            .naglowek( NaglowekDekalaracji.create()
                                    .kwartal( period.getBegin().get( IsoFields.QUARTER_OF_YEAR)))
                            .pozycjeSzczegolowe( pozycjeSzczegolowe()))
                : Ewidencja_JPK_V7K_2_v1_0e.create())
                .ewidencja( Ewidencja.create()
                        .sprzedazCtrl( sprzedazCtrl)
                        .sprzedazWiersz( fakturySprzedazy( sprzedazCtrl))
                        .zakupCtrl( zakupCtrl)
                        .zakupWiersz( fakturyZakupu( zakupCtrl)));
    }

    private Deklaracja_VAT_7K_16_1_0e.PozycjeSzczegolowe pozycjeSzczegolowe() {

        var pozycjeSzczegolowe= Deklaracja_VAT_7K_16_1_0e.PozycjeSzczegolowe.create();

        // Domestic sales divided by tax rate
        lines.sumSalesByKindAndPeriodGroupByRate( InvoiceRegisterKind.D, period.getParent())
                .forEach( sum-> pozycjeSzczegolowe
                        .apply( salesDomesticFunctions.get( sum.getTaxRate()), sum.getBase(), sum.getVat()));

        // Other sales divided by invoice register kind
        /**
        lines.sumSalesByKindAndItemTypeGroupByType(
                List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W), period.getParent());*/

        // Other purchase
        lines.sumPurchaseByKindAndItemTypeGroupByType(
                List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W), period.getParent())
                        .forEach( sum-> pozycjeSzczegolowe
                            .apply( purchaseOtherFunctions.get( sum.getPurchaseKind()).get( sum.getItemType()),
                                    sum.getBase(), sum.getVat()));

        // All purchase
        lines.sumPurchaseByTypeAndPeriodGroupByType( period.getParent())
                .forEach( sum-> pozycjeSzczegolowe
                        .apply( purchaseFunctions.get( sum.getItemType()), sum.getBase(), sum.getVat()));

        return pozycjeSzczegolowe.summarize();
    }

    private List<SprzedazWiersz> fakturySprzedazy( Ewidencja.SprzedazCtrl sprzedazCtrl ) {
        return lines.findByKindAndPeriodGroupByRate(
                        List.of( InvoiceRegisterKind.D ),
                        List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W ),
                        period ).stream()
            .collect( Collectors.groupingBy( JpaInvoiceSumDto::getInvoiceId, LinkedHashMap::new,
                        Collectors.collectingAndThen( Collectors.toList(),  // for ascending order
                lines-> lines.stream()
                    .findFirst()
                    .map( invoice-> {

                        var sprzedazWiersz = SprzedazWiersz.create()
                            .lpSprzedazy( sprzedazCtrl.addLiczbaWierszy())
                            .dowodSprzedazy( invoice.getInvoiceNumber() )
                            .dataWystawienia( invoice.getIssueDate().format( formatter ) )
                            .dataSprzedazy( Optional.ofNullable(
                                    invoice.getInvoiceDate().isEqual( invoice.getIssueDate() ) ?
                                            null : invoice.getInvoiceDate().format( formatter ) ) )
                            .nazwaKontrahenta( invoice.getEntityName() )
                            .nrKontrahenta( invoice.getTaxNumber() )
                            .kodKrajuNadaniaTIN( Optional.ofNullable(
                                    "PL".equals( invoice.getEntityCountry() ) ?
                                            null : invoice.getEntityCountry() ) );

                        Optional.ofNullable( invoice.getSalesKind() )
                            .ifPresent( kind-> {
                                if( InvoiceRegisterKind.D == kind ) {
                                    lines.forEach( line-> {
                                        sprzedazCtrl.addPodatekNalezny( invoice.getVat());
                                        sprzedazWiersz.apply( salesDomesticRowFunctions
                                            .get( line.getTaxRate()), line.getBase(), line.getVat());
                                    });
                                }
                            });

                        Optional.ofNullable( invoice.getPurchaseKind() )
                            .ifPresent( kind->
                                lines.forEach( line-> {
                                    sprzedazCtrl.addPodatekNalezny( invoice.getVat());
                                    sprzedazWiersz.apply( purchaseOtherRowFunctions
                                        .get( kind ).get( line.getItemType()), line.getBase(), line.getVat());
                                })
                            );

                        return sprzedazWiersz;
                    }).get()
            )))
                .values()
                .stream()
                .toList();
    }

    private List<ZakupWiersz> fakturyZakupu( Ewidencja.ZakupCtrl zakupCtrl) {
        return lines.findByKindAndPeriodGroupByType( period).stream()
            .collect( Collectors.groupingBy( JpaInvoiceSumDto::getInvoiceId, LinkedHashMap::new,
                        Collectors.collectingAndThen( Collectors.toList(),  // for ascending order
                lines-> lines.stream()
                    .findFirst()
                    .map( invoice-> {
                        var zakupWiersz= Ewidencja.ZakupWiersz.create()
                            .lpZakupu( zakupCtrl.addLiczbaWierszy())
                            .dowodZakupu( invoice.getInvoiceNumber())
                            .dataZakupu( invoice.getIssueDate().format( formatter))
                            .dataWplywu( Optional.ofNullable(
                                    invoice.getInvoiceDate().isEqual( invoice.getIssueDate()) ?
                                            null : invoice.getInvoiceDate().format( formatter)))
                            .nazwaDostawcy( invoice.getEntityName())
                            .nrDostawcy( invoice.getTaxNumber())
                            .kodKrajuNadaniaTIN( Optional.ofNullable(
                                    "PL".equals( invoice.getEntityCountry()) ?
                                            null : invoice.getEntityCountry()));

                        lines.forEach( line-> {
                            zakupCtrl.addPodatekNaliczony( line.getVat());
                            zakupWiersz.apply( purchaseRowFunctions
                                    .get( line.getItemType()), line.getBase(), line.getVat());
                        });

                        return zakupWiersz;
                    }).get()
            )))
                .values()
                .stream()
                .toList();
    }


    /**
        The last month of the quarter is March (3), June (6), September (9), or December (12)
     */
    private boolean isLastMonthOfQuarter( LocalDate date) {
        return date.getMonth().getValue() % 3 == 0;
    }

}
