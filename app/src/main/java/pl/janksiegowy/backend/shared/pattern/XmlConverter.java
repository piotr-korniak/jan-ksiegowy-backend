package pl.janksiegowy.backend.shared.pattern;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlConverter<T> {



    public static String marshal( Object pattern ) {
        try {
            var marshaller= JAXBContext.newInstance( pattern.getClass()).createMarshaller();
            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            var result= new StringWriter();
            marshaller.marshal( pattern, result);

            return result.toString();
        } catch (JAXBException e) {
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



