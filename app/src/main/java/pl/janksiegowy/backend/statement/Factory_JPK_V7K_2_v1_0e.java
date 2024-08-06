package pl.janksiegowy.backend.statement;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.stereotype.Component;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e.Naglowek;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e.Podmiot1;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e.Ewidencja;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e.Deklaracja_VAT_7K_16_1_0e;

import pl.gov.crd.wzor._2021._12._27._11149.TKodFormularza;
import pl.gov.crd.wzor._2021._12._27._11149.TKodFormularzaVATK;
import pl.janksiegowy.backend.metric.Metric;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.statement.dto.StatementMap;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.IsoFields;
import java.util.*;

@Component
@AllArgsConstructor
public class Factory_JPK_V7K_2_v1_0e extends Factory_JPK_V7 {

    //private final VatSpecificItems vatItems;

    private final Map<TaxRate, TriConsumer<Ewidencja.SprzedazWiersz, BigDecimal, BigDecimal>>
            salesDomesticRowFunctions= Map.of(
                TaxRate.S1, (sprzedazWiersz, base, vat)-> {
                    sprzedazWiersz.setK19( Util.sum( sprzedazWiersz.getK19(), base));
                    sprzedazWiersz.setK20( Util.sum( sprzedazWiersz.getK20(), vat));
    });

    @Override
    public void prepare( StatementMap statementMap) {

    }

    @Override protected Statement_JPK_V7 prepare( Metric metric) {

        //vatItems.calculate()

        var ewidencja= new Ewidencja_JPK_V7K_2_1_0e();
        ewidencja.setNaglowek( new Naglowek() {{
            wariantFormularza= 2;
            setKodFormularza( new KodFormularza(){{
                value= TKodFormularza.JPK_VAT;
                kodSystemowy= getKodSystemowy();
                wersjaSchemy= getWersjaSchemy();
            }});

            setDataWytworzeniaJPK( Util.gregorianNow());
            setCelZlozenia( new CelZlozenia() {{
                value= reason;
                setPoz( getPoz());
            }} );

            setKodUrzedu( metric.getRcCode());
            setRok( Util.toGregorianYear( period.getBegin()));
            setMiesiac( (byte)period.getBegin().getMonthValue());

        }});


        ewidencja.setPodmiot1( new Podmiot1() {{
            setRola( getRola());
            setOsobaNiefizyczna( new OsobaNiefizyczna() {{
                setNIP( metric.getTaxNumber());
                setPelnaNazwa( metric.getName());
                setTelefon( "601-528-601");
                setEmail( "info@eleutheria.pl");
            }} );
        }});

        ewidencja.setEwidencja( new Ewidencja() {{
            var sprzedazCtrl= new SprzedazCtrl();
            setSprzedazCtrl( sprzedazCtrl);

            salesInvoice.values().forEach( lines-> {
                lines.stream()
                    .findFirst()
                    .ifPresent( invoice-> getSprzedazWiersz()
                        .add( new SprzedazWiersz() {{
                            sprzedazCtrl.setLiczbaWierszySprzedazy(
                                    Util.sum( sprzedazCtrl.getLiczbaWierszySprzedazy(), BigInteger.ONE));
                            setLpSprzedazy( sprzedazCtrl.getLiczbaWierszySprzedazy());

                            setDowodSprzedazy( invoice.getInvoiceNumber());
                            setDataWystawienia( Util.toGregorian( invoice.getIssueDate()));
                            if( !invoice.getInvoiceDate().isEqual( invoice.getIssueDate()))
                                setDataSprzedazy( Util.toGregorian( invoice.getInvoiceDate()));
                            setNazwaKontrahenta( invoice.getEntityName());
                            setNrKontrahenta( invoice.getTaxNumber());
                            if( !"PL".equals( invoice.getEntityCountry()))
                                setKodKrajuNadaniaTIN( invoice.getEntityCountry());

                            Optional.ofNullable( invoice.getSalesKind())
                                .ifPresent( kind-> {
                                    if( InvoiceRegisterKind.D== kind) {
                                        lines.forEach( line-> {
                                            sprzedazCtrl.setPodatekNalezny(
                                                    Util.sum( sprzedazCtrl.getPodatekNalezny(), line.getVat()));
                                            salesDomesticRowFunctions.get( line.getTaxRate())
                                                    .accept( this, line.getBase(), line.getVat());
                                        });
                                    }});
                            Optional.ofNullable( invoice.getPurchaseKind())
                                .ifPresent( kind-> {
                                    sprzedazCtrl.setPodatekNalezny(
                                            Util.sum( sprzedazCtrl.getPodatekNalezny(), invoice.getVat()));
                                    switch ( kind) {
                                        case W -> {
                                            setK27( Util.addOrAddend( getK27(), invoice.getBase()));
                                            setK28( Util.addOrAddend( getK28(), invoice.getVat()));}
                                    }
                                });

                            }}));


            });

            var zakupCtrl= new ZakupCtrl();
            setZakupCtrl( zakupCtrl);
            purchaseInvoice.values().forEach( lines-> {
                lines.stream()
                    .findFirst()
                    .ifPresent( invoice -> getZakupWiersz()
                        .add( new ZakupWiersz() {{
                            zakupCtrl.setLiczbaWierszyZakupow(
                                    Util.sum( zakupCtrl.getLiczbaWierszyZakupow(), BigInteger.ONE));
                            setLpZakupu( zakupCtrl.getLiczbaWierszyZakupow());

                            setDowodZakupu( invoice.getInvoiceNumber());
                            setDataZakupu( Util.toGregorian( invoice.getInvoiceDate()));

                            var receiptDate= Util.max( period.getBegin(), invoice.getIssueDate());
                            if( !invoice.getInvoiceDate().isEqual( receiptDate))
                                setDataWplywu( Util.toGregorian( receiptDate));
                            setNazwaDostawcy( invoice.getEntityName());
                            setNrDostawcy( invoice.getTaxNumber());
                            if( !"PL".equals( invoice.getEntityCountry()))
                                setKodKrajuNadaniaTIN( invoice.getEntityCountry());

                            lines.forEach( line-> {
                                zakupCtrl.setPodatekNaliczony(
                                        Util.sum( zakupCtrl.getPodatekNaliczony(), line.getVat()));
                                switch ( line.getItemType()) {
                                    case A-> {
                                        setK40( Util.sum( getK40(), line.getBase()));
                                        setK41( Util.sum( getK41(), line.getVat()));}
                                    case S, M, P-> {
                                        setK42( Util.sum( getK42(), line.getBase()));
                                        setK43( Util.sum( getK43(), line.getVat()));}
                                }
                            });
                        }}));
            });

        }});

        if( isSettlement()) {
            ewidencja.setDeklaracja( new Deklaracja_VAT_7K_16_1_0e() {{
                pouczenia= BigDecimal.ONE;
                setNaglowek( new Naglowek() {{
                   wariantFormularzaDekl= 16;
                   setKodFormularzaDekl( new KodFormularzaDekl() {{
                       kodSystemowy= getKodSystemowy();
                       kodPodatku= getKodPodatku();
                       rodzajZobowiazania= getRodzajZobowiazania();
                       wersjaSchemy= getWersjaSchemy();
                       value= TKodFormularzaVATK.VAT_7_K;

                   }});

                   setKwartal( (byte) period.getBegin().get( IsoFields.QUARTER_OF_YEAR));
                }});

                setPozycjeSzczegolowe( new PozycjeSzczegolowe() {{

                    setP19( Util.toBigIntegerOrNull( account.getVariable( "Sprzedaz_Netto_S1")));
                    setP20( Util.toBigIntegerOrNull( account.getVariable( "Sprzedaz_Vat_S1")));

                    setP27( Util.toBigIntegerOrNull( account.getVariable( "Import_Uslug_Netto")));
                    setP28( Util.toBigIntegerOrNull( account.getVariable( "Import_Uslug_Vat")));

                    setP40( Util.toBigIntegerOrNull( account.getVariable( "Zakupy_Trwale_Netto")));
                    setP41( Util.toBigIntegerOrNull( account.getVariable( "Zakupy_Trwale_Vat")));
                    setP42( Util.toBigIntegerOrNull( account.getVariable( "Zakupy_Pozostale_Netto")));
                    setP43( Util.toBigIntegerOrNull( account.getVariable( "Zakupy_Pozostale_Vat")));

                    setP37( Util.toBigIntegerOrZero( account.getVariable( "Razem")));
                    setP38( Util.toBigIntegerOrZero( account.getVariable( "Razem_Nalezny")));
                    setP48( Util.toBigIntegerOrZero( account.getVariable( "Razem_Naliczony")));

                    setP51( Util.toBigIntegerOrZero( account.getVariable( "Kwota_Zobowiazania")));

               }});

            }});

        }

        return ewidencja;
    }



/*
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
*/
/*
    public Ewidencja_JPK_V7K_2_v1_0e_old create( MonthPeriod period) {
        var sprzedazCtrl=  Ewidencja.SprzedazCtrl.create();
        var zakupCtrl= Ewidencja.ZakupCtrl.create();

        //var metric= metrics.findByDate( period.getBegin()).orElseThrow();


        MetricDto.Proxy metric;

        return (isLastMonthOfQuarter( period.getBegin())
                ? Ewidencja_JPK_V7K_2_v1_0e_old.create()
                    .deklaracja( Deklaracja_VAT_7K_16_1_0e_old.create()
                            .naglowek( NaglowekDekalaracji.create()
                                    .kwartal( period.getBegin().get( IsoFields.QUARTER_OF_YEAR)))
                            .pozycjeSzczegolowe( pozycjeSzczegolowe( period)))
                : Ewidencja_JPK_V7K_2_v1_0e_old.create())
                .naglowek( TNaglowek.create()
                        .kodUrzedu( metric.getRcCode())
                        .nazwaSystemu( applicationName)
                        .rok( period.getBegin().getYear())
                        .miesiac( period.getBegin().getMonthValue())
                        .dataWytworzeniaJPK( LocalDateTime.now()
                                .format( DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ss"))+ "Z"))
                .podmiot1( Podmiot1.create()
                        .osobaNiefizyczna( OsobaNiefizyczna.create()
                                .nip( metric.getTaxNumber())
                                .pelnaNazwa( metric.getName())
                                .email( "info@eleutheria.pl")
                                .telefon( "601-528-601")))
                .ewidencja( Ewidencja.create()
                        .sprzedazCtrl( sprzedazCtrl)
                        .sprzedazWiersz( fakturySprzedazy( sprzedazCtrl, period))
                        .zakupCtrl( zakupCtrl)
                        .zakupWiersz( fakturyZakupu( zakupCtrl, period)));
    }
*/

/*
    private Deklaracja_VAT_7K_16_1_0e_old.PozycjeSzczegolowe pozycjeSzczegolowe( MonthPeriod period) {

        var pozycjeSzczegolowe= Deklaracja_VAT_7K_16_1_0e_old.PozycjeSzczegolowe.create();

        // Domestic sales divided by tax rate
        lines.sumSalesByKindAndPeriodGroupByRate( InvoiceRegisterKind.D, period.getParent())
                .forEach( sum-> pozycjeSzczegolowe
                        .apply( salesDomesticFunctions.get( sum.getTaxRate()), sum.getBase(), sum.getVat()));

        // Other sales divided by invoice register kind
        /**
        lines.sumSalesByKindAndItemTypeGroupByType(
                List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W), period.getParent());*/

        // Other purchase
/*        lines.sumPurchaseByKindAndItemTypeGroupByType(
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

    private List<SprzedazWiersz> fakturySprzedazy( Ewidencja.SprzedazCtrl sprzedazCtrl, MonthPeriod period) {
        return lines.findByKindAndPeriodIdGroupByRate(
                        List.of( InvoiceRegisterKind.D ),
                        List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W ),
                        period.getId())
                    .stream()
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

    private List<ZakupWiersz> fakturyZakupu( Ewidencja.ZakupCtrl zakupCtrl, MonthPeriod period) {
        return lines.findByKindAndPeriodGroupByType( period.getId())
                    .stream()
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

*/

    /**
     The last month of the quarter is March (3), June (6), September (9), or December (12)
     */
    @Override protected boolean isSettlement() {
        return period.getEnd().getMonthValue()% 3 == 0;
    }



    @Override protected String getNumber() {
        return "VAT-7K "+ period.getEnd().getYear()+ "K"+
                (( period.getEnd().getMonth().getValue()- 1) / 3 + 1);
    }

    @Override protected Period getPeriod() {
        return isSettlement()? period.getParent(): period;
    }
}
