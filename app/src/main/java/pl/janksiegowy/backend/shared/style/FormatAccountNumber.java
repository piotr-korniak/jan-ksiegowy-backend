package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class FormatAccountNumber extends Template {
    {
        name= "format-account-number";
    }

    @XmlElement( name= "param", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected Param param= new Param().setName( "number");

    @XmlElement( name= "variable", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected List<Variable> variables= List.of(
            new Variable().setName( "checksum").setSelect( "substring( $number, 1, 2)"),
            new Variable().setName( "mainPart").setSelect( "substring( $number, 3)"),
            new Variable() {{
                name= "formatted";
                setValueOf( new ValueOf().setSelect( "$checksum"));
                setForEach( new ForEach() {{
                    select= "for $i in 1 to string-length( $mainPart) div 4 " +
                            "return ' ' || substring( $mainPart, ($i - 1) * 4 + 1, 4)";
                    valueOf= new ValueOf().setSelect( ".");
                }});

            }}
    );

    @XmlElement( name= "value-of")
    protected ValueOf valueOf= new ValueOf().setSelect( "$formatted");


}
