package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.janksiegowy.backend.invoice.dto.InvoiceViewDto;
import pl.janksiegowy.backend.invoice_fop.InvoicePdfGenerator;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.subdomain.TenantController;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;
import java.util.UUID;

//@RestController
@TenantController
@RequestMapping( "/v2/invoices")
@AllArgsConstructor
public class InvoiceController {

    private final InvoiceQueryRepository query;
    private final InvoiceRepository invoices;

    @GetMapping
    ResponseEntity<List<InvoiceViewDto>> list() {
        return ResponseEntity.ok( query.findBy( InvoiceViewDto.class));
    }

    @GetMapping( value = "/pdf/{invoiceId}", produces= "application/pdf")
    public ResponseEntity<byte[]> getInvoicePdf( @PathVariable UUID invoiceId) {

        return invoices.findByInvoiceId( invoiceId).map( invoice -> {
            try {
                var xslFo= new InvoicePdfGenerator().generate( invoice);
                var fopFactory= FopFactory.newInstance(
                        new File( "./src/main/resources/fop/fop.xml"));
                var outStream= new ByteArrayOutputStream();

                // Setup FOP transformer
                var fop= fopFactory.newFop( MimeConstants.MIME_PDF, outStream);
                TransformerFactory.newInstance().newTransformer()
                        .transform( new StreamSource( new StringReader( xslFo)),
                                new SAXResult( fop.getDefaultHandler()));

                // Set HTTP headers
                var headers= new HttpHeaders();
                headers.set( HttpHeaders.CONTENT_TYPE, "application/pdf");
                headers.set( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+
                        Util.sanitizeFileName( "Faktura_"+invoice.getNumber())+ ".pdf");

                // Return PDF as response
                return new ResponseEntity<byte[]>( outStream.toByteArray(), headers, HttpStatus.OK);

            } catch ( Exception e) {
                e.printStackTrace();
                return new ResponseEntity<byte[]>( HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }).orElseGet( ()->
                ResponseEntity.status( HttpStatus.NOT_FOUND)
                        .body(( "Invoice with UUID: " + invoiceId+ " not found!").getBytes()));
    }
}
