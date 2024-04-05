package pl.janksiegowy.backend.statement;

import pl.gov.crd.wzor._2024._02._08._13272.Deklaracja_CIT_8_33_v2_0e;
import pl.gov.crd.wzor._2024._02._08._13272.Deklaracja_CIT_8_33_v2_0e.Podmiot1;
import pl.gov.crd.wzor._2024._02._08._13272.TAdresPolski1;
import pl.gov.crd.wzor._2024._02._08._13272.TKodFormularza;
import pl.gov.crd.wzor._2024._02._08._13272.TNaglowek;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._09._13.ed.definicjetypy.TIdentyfikatorOsobyNiefizycznej1;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2023._09._06.ed.kodykrajow.TKodKraju;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;
import java.math.BigInteger;


public class Factory_CIT_8_33_v2_0e extends Factory_CIT_8 {

    @Override public Settlement_CIT_8 prepare() {
        var deklaracja= new Deklaracja_CIT_8_33_v2_0e();

        deklaracja.setNaglowek( new TNaglowek(){{
            wariantFormularza= 33;
            kodUrzedu= metric.getRcCode();

            setCelZlozenia( new CelZlozenia(){{
                value= 1;
                setPoz( getPoz());
            }});
            setKodFormularza( new KodFormularza() {{
                value= TKodFormularza.CIT_8;
                kodPodatku= getKodPodatku();
                kodSystemowy= getKodSystemowy();
                rodzajZobowiazania= getRodzajZobowiazania();
                wersjaSchemy= getWersjaSchemy();
            }});
            setOkresOd( new OkresOd(){{
                value= Util.toGregorian( period.getBegin());
                setPoz( getPoz());
            }});
            setOkresDo( new OkresDo(){{
                value= Util.toGregorian( period.getEnd());
                setPoz( getPoz());
            }});
        }});

        deklaracja.setPodmiot1( new Podmiot1() {{
            setRola( getRola());
            setOsobaNiefizyczna( new TIdentyfikatorOsobyNiefizycznej1() {{
                nip= metric.getTaxNumber();
                pelnaNazwa= metric.getName();
            }});

            setAdresSiedziby( new AdresSiedziby() {{
                setRodzajAdresu( getRodzajAdresu());
                setAdresPol( new TAdresPolski1() {{
                    kodKraju= TKodKraju.PL;
                    wojewodztwo= "POMORSKIE";
                    powiat= "WEJHEROWSKI";
                    gmina= "RUMIA";
                    ulica= "WIEJSKA";
                    nrDomu= "24A";
                    miejscowosc= metric.getTown().toUpperCase();
                    kodPocztowy= metric.getPostcode();
                }});
            }});
        }});

        deklaracja.setPozycjeSzczegolowe( new Deklaracja_CIT_8_33_v2_0e.PozycjeSzczegolowe() {{
            p26= 3;
            p33= 2;
            setP53( data.getVariable( "przychody"));
            p60= BigDecimal.ZERO;
            setP61( data.getVariable( "przychody"));
            setP63( data.getVariable( "koszty"));
            p76= BigDecimal.ZERO;
            setP77( data.getVariable( "koszty"));
            setP79( data.getVariable( "wynik"));
            setP85( data.getVariable( "wynik"));
            setP95( data.getVariable( "wynik"));
            setP114( data.getVariable( "wynik"));
            p118= BigDecimal.ZERO;
            p119= BigDecimal.ZERO;
            setP120( data.getVariable( "wynik"));
            setP131( data.getVariable( "wynik"));
            setP138( data.getVariable( "podstawa").toBigInteger());
            setP140( data.getVariable( "podstawa").toBigInteger());
            p1422= 1;
            setP144( data.getVariable( "podatek"));
            setP146( data.getVariable( "podatek"));
            setP148( data.getVariable( "podatek_int").toBigInteger());
            setP247( data.getVariable( "podatek").toBigInteger());
            setP259( data.getVariable( "podatek").toBigInteger());
            p315= 2;
            p316= 2;
            p317= 2;
            p318= 2;
            p320= 2;
            p321= 2;

        }});

        deklaracja.setPouczenia( BigDecimal.ONE);

        return deklaracja;
    }

}
