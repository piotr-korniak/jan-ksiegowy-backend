package pl.janksiegowy.backend.shared.pattern;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;


public class XmlTransformer {

    public static String transformXmlWithXslt( String xml, String xslt) throws Exception {
        TransformerFactory transformerFactory= TransformerFactory.newInstance();
        StreamSource xsltStream= new StreamSource(new StringReader(xslt));

        Transformer transformer = transformerFactory.newTransformer( xsltStream);

        StringWriter resultWriter= new StringWriter();

        transformer.transform( new StreamSource( new StringReader(xml)),
                new StreamResult( resultWriter));


        return XmlConverter.transformXml( resultWriter.toString()).toString();
    }
}
