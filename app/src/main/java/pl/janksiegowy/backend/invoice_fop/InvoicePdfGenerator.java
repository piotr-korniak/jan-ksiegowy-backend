package pl.janksiegowy.backend.invoice_fop;

import pl.janksiegowy.backend.invoice_fop.dto.TemplateFunction;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.shared.pattern.XmlTransformer;
import pl.janksiegowy.backend.shared.style.StyleSheet;
import pl.janksiegowy.backend.shared.style.Template;

import java.util.Collection;
import java.util.List;

public class InvoicePdfGenerator {
     private List<TemplateFunction> getHeaderFields() {
         return List.of(
             TemplateFunction.create()
                     .code( "TaxNumber")
                     .label( "NIP: ")
                     .function( "*[name()='Faktura']/*[name()='Podmiot1']/"+
                                "*[name()='DaneIdentyfikacyjne']/*[name()='NIP']"));
     }
/*
     public void dupa() {
         return new StyleSheet()
             .add( new Template( "/"){{
             }});
     }
    */
    public String generate() {

        var xml= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Faktura> xmlns=\"http://crd.gov.pl/wzor/2023/06/29/12648/\">\n" +
                "   <Naglowek>\n" +
                "      <KodFormularza kodSystemowy=\"FA (2)\" wersjaSchemy=\"1-0E\">FA</KodFormularza>\n" +
                "      <WariantFormularza>2</WariantFormularza>\n" +
                "   </Naglowek>\n" +
                "   <Podmiot1>\n" +
                "      <DaneIdentyfikacyjne>\n" +
                "         <NIP>5862321911</NIP>\n" +
                "         <Nazwa>Eleutheria Usługi Informatyczne Sp. z o.o.</Nazwa>\n" +
                "      </DaneIdentyfikacyjne>\n" +
                "      <Adres>\n" +
                "         <KodKraju>PL</KodKraju>\n" +
                "         <AdresL1>ul. Wiejska 24a</AdresL1>\n" +
                "         <AdresL2>84-230 Rumia</AdresL2>\n" +
                "      </Adres>\n" +
                "   </Podmiot1>\n" +
                "</Faktura>";
        var xslt= XmlConverter.marshal( new FactoryInvoiceStyle().prepare());
        System.err.println( xslt);
        try {
            return XmlTransformer.transformXmlWithXslt( xml, xslt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

}
