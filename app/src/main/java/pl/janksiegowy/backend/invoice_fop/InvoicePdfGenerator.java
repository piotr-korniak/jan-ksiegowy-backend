package pl.janksiegowy.backend.invoice_fop;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.invoice.Invoice;
import pl.janksiegowy.backend.invoice.InvoiceRepository;
import pl.janksiegowy.backend.shared.pattern.XmlConverter;
import pl.janksiegowy.backend.shared.pattern.XmlTransformer;

import java.util.UUID;

@AllArgsConstructor
public class InvoicePdfGenerator {

    public String generate( Invoice invoice) {
        try {
            return XmlTransformer.transformXmlWithXslt( invoice.getXml(),
                    XmlConverter.marshal( new FactoryInvoiceStyle().prepare())
                            .replace( "&gt;", ">"));
        } catch ( Exception e) {
            throw new RuntimeException( e);
        }
    }
}
