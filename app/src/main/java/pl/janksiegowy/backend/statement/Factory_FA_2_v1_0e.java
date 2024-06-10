package pl.janksiegowy.backend.statement;

import pl.gov.crd.wzor._2023._06._29._12648.*;
import pl.gov.crd.wzor._2023._06._29._12648.Faktura_FA_2.Podmiot1;
import pl.gov.crd.wzor._2023._06._29._12648.Faktura_FA_2.Podmiot2;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2022._01._05.ed.definicjetypy.TKodKraju;
import pl.janksiegowy.backend.invoice.SalesInvoice;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Factory_FA_2_v1_0e extends Factory_FA {

    @Override
    public Invoice_FA prepare( SalesInvoice invoice) {
        return new Faktura_FA_2()
               .setNaglowek( new TNaglowek() {{
                   wariantFormularza= 2;
                   setKodFormularza( new KodFormularza() {{
                       value= TKodFormularza.FA;
                       kodSystemowy= getKodSystemowy();
                       wersjaSchemy= getWersjaSchemy();
                   }});

            }}).setPodmiot1( new Podmiot1() {{
                var metric= invoice.getMetric();
                setDaneIdentyfikacyjne( new TPodmiot1() {{
                    setNIP( metric.getTaxNumber());
                    setNazwa( metric.getName());
                    setAdres( new TAdres() {{
                        kodKraju= TKodKraju.fromValue( metric.getCountry());
                        adresL1= metric.getAddress();
                        adresL2= metric.getPostcode()+ " "+ metric.getTown();
                    }});
                }});
            }}).setPodmiot2( new Podmiot2() {{
                var entity= invoice.getEntity();
                setDaneIdentyfikacyjne( new TPodmiot2() {{
                    setNIP( entity.getTaxNumber());
                    setNazwa( entity.getName());
                    setAdres( new TAdres() {{
                        kodKraju= TKodKraju.fromValue( entity.getCountry().name());
                        adresL1= entity.getAddress();
                        adresL2= entity.getPostcode()+ " "+ entity.getTown();
                    }});
                }});
            }}).setFa( new Faktura_FA_2.Fa() {{
                p1= Util.toGregorian( invoice.getInvoiceDate());
                p1M= invoice.getRegister().getName();
                p2= invoice.getNumber();
                p6= Util.toGregorian( invoice.getIssueDate());
                p15= invoice.getSubTotal().add( invoice.getTaxTotal());

                setPlatnosc( new Platnosc() {{
                    this.dataZaplaty= Util.toGregorian( invoice.getDueDate());
                    if( invoice.getPaymentMetod()!= null)
                        setFormaPlatnosci( BigInteger.valueOf( invoice.getPaymentMetod().ordinal()+ 1));


                }});


                invoice.getLineItems().stream().forEach( invoiceLine-> {
                    switch ( invoiceLine.getTaxRate()) {
                        case S1-> {
                            p131= Util.addOrAddend( p131, invoiceLine.getAmount());
                            p141= Util.addOrAddend( p141, invoiceLine.getTax());
                        }
                        case S2-> {
                            p132= Util.addOrAddend( p132, invoiceLine.getAmount());
                            p142= Util.addOrAddend( p142, invoiceLine.getTax());
                        }
                    }});

                final var no= new Object(){ BigInteger no= BigInteger.ZERO;};
                invoice.getLineItems()
                    .forEach( invoiceLine -> getFaWiersz().add( new FaWiersz() {{
                        uuid= invoiceLine.getId().toString();
                        nrWierszaFa= no.no= Util.sum( no.no, BigInteger.ONE);
                        p7= invoiceLine.getItem().getName();
                        p8A= invoiceLine.getItem().getMeasure();
                        p8B= BigDecimal.ONE;            //  <- fixme
                        p9A= invoiceLine.getAmount();   //  <- fixme
                        p11= invoiceLine.getAmount();
                        p11A= invoiceLine.getAmount().add( invoiceLine.getVat());
                        p11Vat= invoiceLine.getVat();
                        p12= switch ( invoiceLine.getTaxRate()) {
                            case S1-> "23";
                            case S2-> "8";
                            default ->
                                    throw new IllegalStateException( "Unexpected value: "+ invoiceLine.getTaxRate());
                        };
                    }}));
            }});
    }
}
