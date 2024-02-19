package pl.janksiegowy.backend.shared.pattern;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;

public class XmlGenerator<T> implements PatternId.PatternIdVisitor<T> {

    public String marshal( Object wzorzec) {
        var result= new StringWriter();

        try {
            var marshaller= JAXBContext
                    .newInstance( wzorzec.getClass()).createMarshaller();

            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal( wzorzec, result);
        } catch (JAXBException e) {
            throw new RuntimeException( e );
        }
        return result.toString();
    }

    @Override public T visit_JPK_V7K_2_v1_0e() {
        return null;
    }

    @Override public T visit_FA_2_v1_0e() {
        return null;
    }
}



