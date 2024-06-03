package pl.janksiegowy.backend.shared.style;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType( XmlAccessType.FIELD)
@XmlType( name= "template", namespace= "")
public class NumberToWords extends Template {
    {
        name= "number-to-words";
    }

    @XmlElement( name= "param", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected Param param= new Param().setName( "number");

    @XmlElement( name= "variable", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected List<Variable> variables = List.of(
            new Variable().setName( "zlote").setSelect( "floor($number)"),
            new Variable().setName( "grosze").setSelect( "round(($number - $zlote) * 100)"),

            new Variable().setName( "jednosci").setSelect( "$zlote mod 10"),
            new Variable().setName( "zlote").setSelect( "floor($zlote * 0.1)"),
            new Variable().setName( "dziesiatki").setSelect( "$zlote mod 10"),
            new Variable().setName( "zlote").setSelect( "floor($zlote * 0.1)"),
            new Variable().setName( "setki").setSelect( "$zlote mod 10"),
            new Variable().setName( "zlote").setSelect( "floor($zlote * 0.1)"),

            new Variable().setName( "tysiace").setSelect( "$zlote mod 10"),
            new Variable().setName( "zlote").setSelect( "floor($zlote * 0.1)"),
            new Variable().setName( "dziesiatki_tysiecy").setSelect( "$zlote mod 10"),
            new Variable().setName( "zlote").setSelect( "floor($zlote * 0.1)"),
            new Variable().setName( "setki_tysiecy").setSelect( "$zlote mod 10"),
            new Variable().setName( "zlote").setSelect( "floor($zlote * 0.1)")
    );

    @XmlElement( name= "if", namespace= "http://www.w3.org/1999/XSL/Transform")
    protected List<If> ifs= List.of(
            new If().setTest( "$setki_tysiecy > 0 and ($dziesiatki_tysiecy + $tysiace)> 0")
                    .setValueOf( new ValueOf() {{
                        select= "$setki_lista[$setki_tysiecy]";}}),
            new If().setTest( "$setki_tysiecy > 0 and ($dziesiatki_tysiecy + $tysiace)= 0")
                    .setValueOf( new ValueOf() {{
                        select= "$setki_lista[$setki_tysiecy] || $tysiace_lista[3]";}}),
            new If().setTest( "$dziesiatki_tysiecy > 1 and $tysiace > 0")
                    .setValueOf( new ValueOf() {{
                        select= "$dziesiatki_lista[$dziesiatki_tysiecy - 1]";}}),
            new If().setTest( "$dziesiatki_tysiecy > 1 and $tysiace = 0")
                    .setValueOf( new ValueOf() {{
                        select= "$dziesiatki_lista[$dziesiatki_tysiecy - 1] || $tysiace_lista[3]";}}),
            new If().setTest( "$dziesiatki_tysiecy = 1")
                    .setValueOf( new ValueOf() {{
                        select= "$nastki_lista[$tysiace + 1] || $tysiace_lista[3]";}}),
            new If().setTest( "$dziesiatki_tysiecy != 1 and $tysiace > 1 and $tysiace < 5")
                    .setValueOf( new ValueOf() {{
                        select= "$jednosci_lista[$tysiace + 1] || $tysiace_lista[2]";}}),
            new If().setTest( "$dziesiatki_tysiecy != 1 and ($tysiace > 4 or " +
                              "($tysiace = 1 and ($dziesiatki_tysiecy + $setki_tysiecy) != 0))")
                    .setValueOf( new ValueOf() {{
                        select= "$jednosci_lista[$tysiace + 1] || $tysiace_lista[3]";}}),
            new If().setTest( "$tysiace = 1 and ($dziesiatki_tysiecy + $setki_tysiecy) = 0")
                    .setValueOf( new ValueOf() {{
                        select= "$jednosci_lista[2] || $tysiace_lista[1]";}}),
            new If().setTest( "$setki > 0")
                    .setValueOf( new ValueOf() {{ select= "$setki_lista[$setki]";}}),
            new If().setTest( "$dziesiatki > 1")
                    .setValueOf( new ValueOf() {{ select= "$dziesiatki_lista[$dziesiatki - 1]";}}),
            new If().setTest( "$dziesiatki = 1").setValueOf( new ValueOf() {{
                        select= "$nastki_lista[$jednosci + 1]";}}),
            new If().setTest( "$dziesiatki != 1 and $jednosci > 0").setValueOf( new ValueOf() {{
                        select= "$jednosci_lista[$jednosci + 1]";
                    }}),
            new If().setTest( "($setki_tysiecy + $dziesiatki_tysiecy + $tysiace + " +
                               "$setki + $dziesiatki + $jednosci) = 0").setValueOf( new ValueOf() {{
                        select= "$jednosci_lista[1]";}}),
            new If().setTest( "1 = 1").setValueOf( new ValueOf() {{
                        select= "$grosze";}})
                    .setSuffix( new Text().setValue( "/100"))
    );
}

