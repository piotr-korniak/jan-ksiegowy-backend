package pl.janksiegowy.backend.declaration.formatter;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.stereotype.Component;
import pl.gov.crd.wzor._2021._12._27._11149.Ewidencja_JPK_V7K_2_1_0e;
import pl.gov.crd.wzor._2021._12._27._11149.TKodFormularza;
import pl.gov.crd.wzor._2021._12._27._11149.TKodFormularzaVATK;
import pl.janksiegowy.backend.invoice_line.InvoiceLineQueryRepository;
import pl.janksiegowy.backend.invoice_line.dto.JpaInvoiceSumDto;
import pl.janksiegowy.backend.metric.MetricRepository;
import pl.janksiegowy.backend.period.Period;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterKind;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.financial.TaxRate;
import pl.janksiegowy.backend.shared.interpreter.Interpreter;
import pl.janksiegowy.backend.shared.pattern.PatternId;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.declaration.formatter.dto.FormatterDto;
import pl.janksiegowy.backend.tax.TaxType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Formatter_JPK_V7K_2_1_0e implements FormatterStrategy<FormatterDto, Interpreter> {

    private final LocalDate dateApplicable= LocalDate.of( 2020, 1, 1);

    private final Map<TaxRate, TriConsumer<Ewidencja_JPK_V7K_2_1_0e.Ewidencja.SprzedazWiersz, BigDecimal, BigDecimal>>
            salesDomesticRowFunctions= Map.of(
            TaxRate.S1, (sprzedazWiersz, base, vat)-> {
                sprzedazWiersz.setK19( Util.sum( sprzedazWiersz.getK19(), base));
                sprzedazWiersz.setK20( Util.sum( sprzedazWiersz.getK20(), vat));
            });

/*

    private final salesInvoice= lines.findByKindAndPeriodGroupByRate(
            List.of( InvoiceRegisterKind.D),
            List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W),
            period.getId())
            .stream()
                .collect(Collectors.groupingBy(JpaInvoiceSumDto::getInvoiceId,
                         LinkedHashMap::new, Collectors.toList()));
*/
    private final MetricRepository metrics;
    private final InvoiceLineQueryRepository lines;


    @Override public FormatterDto format( Period period, Interpreter result) {
        return FormatterDto.create()
                .content( XmlConverter.marshal( prepare( period, result)))
                .version( PatternId.JPK_V7K_2_1_0e);
    }


    private Ewidencja_JPK_V7K_2_1_0e prepare( Period period, Interpreter result) {
        var ewidencja= new Ewidencja_JPK_V7K_2_1_0e();

        metrics.findByDate( period.getBegin())
            .ifPresent( metric -> {
                ewidencja.setNaglowek( new Ewidencja_JPK_V7K_2_1_0e.Naglowek() {{
                    wariantFormularza= 2;
                    setKodFormularza( new KodFormularza(){{
                        value= TKodFormularza.JPK_VAT;
                        kodSystemowy= getKodSystemowy();
                        wersjaSchemy= getWersjaSchemy();
                    }});

                    setDataWytworzeniaJPK( Util.gregorianNow());
                    setCelZlozenia( new CelZlozenia() {{
                        value= (byte) (result.getVariable( "POWOD", BigDecimal.ONE)
                                .subtract( BigDecimal.ONE).signum()== 0? 1:2);
                        setPoz( getPoz());
                    }} );

                    setKodUrzedu( metric.getRcCode());
                    setRok( Util.toGregorianYear( period.getBegin()));
                    setMiesiac( (byte)period.getBegin().getMonthValue());

                }});

                ewidencja.setPodmiot1( new Ewidencja_JPK_V7K_2_1_0e.Podmiot1() {{
                    setRola( getRola());
                    setOsobaNiefizyczna( new OsobaNiefizyczna() {{
                        setNIP( metric.getTaxNumber());
                        setPelnaNazwa( metric.getName());
                        setTelefon( "601-528-601");
                        setEmail( "info@eleutheria.pl");
                    }} );
                }});


            });



        ewidencja.setEwidencja( new Ewidencja_JPK_V7K_2_1_0e.Ewidencja() {{
            var sprzedazCtrl = new SprzedazCtrl();
            setSprzedazCtrl(sprzedazCtrl);

            lines.findByKindAndPeriodGroupByRate(
                    List.of( InvoiceRegisterKind.D),
                            List.of( InvoiceRegisterKind.U, InvoiceRegisterKind.W),
                            period.getBegin(), period.getEnd())
                    .stream()
                    .collect(Collectors.groupingBy(JpaInvoiceSumDto::getInvoiceId,
                                LinkedHashMap::new, Collectors.toList()))
                        .forEach((invoiceId, lines) -> {

                            lines.stream()
                                    .findFirst()
                                    .ifPresent(invoice -> getSprzedazWiersz()
                                            .add(new SprzedazWiersz() {{
                                                sprzedazCtrl.setLiczbaWierszySprzedazy(
                                                        Util.sum(sprzedazCtrl.getLiczbaWierszySprzedazy(), BigInteger.ONE));
                                                setLpSprzedazy(sprzedazCtrl.getLiczbaWierszySprzedazy());

                                                setDowodSprzedazy(invoice.getInvoiceNumber());
                                                setDataWystawienia(Util.toGregorian(invoice.getIssueDate()));
                                                if (!invoice.getInvoiceDate().isEqual(invoice.getIssueDate()))
                                                    setDataSprzedazy(Util.toGregorian(invoice.getInvoiceDate()));
                                                setNazwaKontrahenta(invoice.getEntityName());
                                                setNrKontrahenta(invoice.getTaxNumber());
                                                if (!"PL".equals(invoice.getEntityCountry()))
                                                    setKodKrajuNadaniaTIN(invoice.getEntityCountry());

                                                Optional.ofNullable(invoice.getSalesKind())
                                                        .ifPresent(kind -> {
                                                            if (InvoiceRegisterKind.D == kind) {
                                                                lines.forEach(line -> {
                                                                    sprzedazCtrl.setPodatekNalezny(
                                                                            Util.sum(sprzedazCtrl.getPodatekNalezny(), line.getVat()));
                                                                    salesDomesticRowFunctions.get(line.getTaxRate())
                                                                            .accept(this, line.getBase(), line.getVat());
                                                                });
                                                            }
                                                        });
                                                Optional.ofNullable(invoice.getPurchaseKind())
                                                        .ifPresent(kind -> {
                                                            sprzedazCtrl.setPodatekNalezny(
                                                                    Util.sum(sprzedazCtrl.getPodatekNalezny(), invoice.getVat()));
                                                            switch (kind) {
                                                                case W -> {
                                                                    setK27(Util.addOrAddend(getK27(), invoice.getBase()));
                                                                    setK28(Util.addOrAddend(getK28(), invoice.getVat()));
                                                                }
                                                            }
                                                        });

                                            }}));


                        });


                var zakupCtrl= new ZakupCtrl();
                setZakupCtrl( zakupCtrl);

            lines.findByKindAndPeriodGroupByType( period)
                    .stream()
                    .collect( Collectors.groupingBy( JpaInvoiceSumDto::getInvoiceId,
                            LinkedHashMap::new, Collectors.toList()))
                    .forEach( (uuidId, lines) -> {


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


            if( period.getEnd().getMonthValue()% 3 == 0) {
                ewidencja.setDeklaracja( new Ewidencja_JPK_V7K_2_1_0e.Deklaracja_VAT_7K_16_1_0e() {{
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

                        setP19( Util.toBigIntegerOrNull( result.getVariable( "Sprzedaz_Netto_S1")));
                        setP20( Util.toBigIntegerOrNull( result.getVariable( "Sprzedaz_Vat_S1")));

                        setP27( Util.toBigIntegerOrNull( result.getVariable( "Import_Uslug_Netto")));
                        setP28( Util.toBigIntegerOrNull( result.getVariable( "Import_Uslug_Vat")));

                        setP40( Util.toBigIntegerOrNull( result.getVariable( "Zakupy_Trwale_Netto")));
                        setP41( Util.toBigIntegerOrNull( result.getVariable( "Zakupy_Trwale_Vat")));
                        setP42( Util.toBigIntegerOrNull( result.getVariable( "Zakupy_Pozostale_Netto")));
                        setP43( Util.toBigIntegerOrNull( result.getVariable( "Zakupy_Pozostale_Vat")));

                        setP37( Util.toBigIntegerOrZero( result.getVariable( "Razem")));
                        setP38( Util.toBigIntegerOrZero( result.getVariable( "Razem_Nalezny")));
                        setP48( Util.toBigIntegerOrZero( result.getVariable( "Razem_Naliczony")));

                        setP51( Util.toBigIntegerOrZero( result.getVariable( "Kwota_Zobowiazania")));

                    }});

                }});

            }

            }

        });

        return ewidencja;
    }

    @Override public boolean isApplicable( TaxType taxType) {
        return taxType== TaxType.VQ;
    }

    @Override public LocalDate getDateApplicable() {
        return dateApplicable;
    }
}
