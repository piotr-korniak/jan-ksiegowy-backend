package pl.janksiegowy.backend.shared.pattern;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlConverter<T> {

    public static StringWriter transformXml( String xmlContent ) throws IOException, TransformerException {
        // Pobierz plik XSLT
        ClassPathResource xsltResource=
                new ClassPathResource( "pattern/remove-unused-namespaces.xsl");

        // Utwórz transformer do wykonania transformacji XSLT
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        var transformer= transformerFactory.newTransformer(
                new StreamSource( xsltResource.getInputStream()));

        // Wykonaj transformację XSLT na pliku XML
        StringWriter resultWriter = new StringWriter();

        // Wykonanie transformacji
        transformer.transform( new StreamSource( new StringReader( xmlContent)), new StreamResult( resultWriter));
        return resultWriter;
    }


    public static String marshal( Object pattern ) {
        try {
            var marshaller= JAXBContext.newInstance( pattern.getClass()).createMarshaller();
            ClassPathResource xsdResource=
                    new ClassPathResource( "pattern/JPK_V7K_2_v1_0e.xsd");
    //        marshaller.setSchema( // Sprawdzenie z szablonem XSD
    //                SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI).
    //                        newSchema( new StreamSource( xsdResource.getInputStream())));

        /*  marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty( Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);       */

            marshaller.setProperty( Marshaller.JAXB_ENCODING, "UTF-8");

            var result= new StringWriter();
            marshaller.marshal( pattern, result);

            return transformXml( result.toString()).toString();
        } catch (JAXBException | IOException | TransformerException e) {//| SAXException e) {
            throw new RuntimeException( e);
        }
    }

    public static <T> T unmarshal( Class<T> type, String xmlData) {
        try {
            var unmarshaller= JAXBContext.newInstance(type).createUnmarshaller();
            return type.cast(unmarshaller.unmarshal( new StringReader(xmlData)));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


}



