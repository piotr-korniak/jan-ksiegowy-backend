package pl.janksiegowy.backend.statement;

import pl.gov.crd.wzor._2023._06._29._12648.*;
import pl.gov.crd.wzor._2023._06._29._12648.Faktura_FA_2.Podmiot1;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TKodKraju;
import pl.janksiegowy.backend.invoice.Invoice;

public class Factory_FA_2_v1_0e extends Factory_FA {

    @Override
    public Invoice_FA prepare( Invoice invoice) {
        var faktura= new Faktura_FA_2();

        faktura.setNaglowek( new TNaglowek(){{
            wariantFormularza= 2;
            setKodFormularza( new KodFormularza(){{
                value= TKodFormularza.FA;
                kodSystemowy= getKodSystemowy();
                wersjaSchemy= getWersjaSchemy();
            }});

        }});

        faktura.setPodmiot1( new Podmiot1(){{
            var metric= invoice.getMetric();

            setDaneIdentyfikacyjne( new TPodmiot1(){{
                setNIP( metric.getTaxNumber());
                setNazwa( metric.getName());
                setAdres( new TAdres() {{
                    kodKraju= TKodKraju.fromValue( metric.getCountry());
                    adresL1= metric.getAddress();
                    adresL2= metric.getPostcode()+ " "+ metric.getTown();
                }});
            }});

        }});

        return faktura;
    }
}
