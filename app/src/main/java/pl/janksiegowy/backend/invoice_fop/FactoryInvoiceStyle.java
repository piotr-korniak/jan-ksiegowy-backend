package pl.janksiegowy.backend.invoice_fop;

import pl.janksiegowy.backend.invoice_fop.dto.TemplateFunction;
import pl.janksiegowy.backend.shared.fop.*;
import pl.janksiegowy.backend.shared.style.StyleSheet;
import pl.janksiegowy.backend.shared.style.ValueOf;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FactoryInvoiceStyle {

    public StyleSheet prepare() {
        return new StyleSheet().setTemplates( List.of( new InvoiceRoot(){{
            setRoot( new Root() {{
                fontSize= 10+ "pt";
                fontFamily= "Arial";
                setLayoutMasterSet( new LayoutMasterSet(){{
                    setSimplePageMasterOrPageSequenceMaster( List.of( new SimplePageMaster(){{
                        masterName= "sales-invoice";
                        marginLeft= 20+ "mm";
                        setRegionBody( new RegionBody());
                    }}));
                }});
                setPageSequence( List.of( new PageSequence() {{
                    masterReference= "sales-invoice";
                    setFlow( new Flow() {{
                        flowName= "xsl-region-body";
                        setContainer( getHeaderFields().stream()
                            .map( tf-> new BlockContainer(){{
                                top= tf.getTop()+ "mm";
                                left= tf.getLeft()+ "mm";
                                setPosition( PositionType.ABSOLUTE);
                                setMarkerOrBlockOrBlockContainer(
                                        Arrays.stream( tf.getFunction().split( "\n"))
                                                .map( fun-> new Block(){{
                                                    value= new ValueOf() {{
                                                        select= transform( fun);
                                                    }};
                                                }})
                                                .collect( Collectors.toList()));
                            }}).collect( Collectors.toList()));
                    }});
                }}));
            }});


        }}));
    }

    private List<TemplateFunction> getHeaderFields() {
        return List.of(
                TemplateFunction.create()
                        .code( "Sprzedawca")
                        .top( 45).left( 2)
                        .function( "@Faktura/Podmiot1/DaneIdentyfikacyjne/Nazwa@\n" +
                                    "@Faktura/Podmiot1/Adres/AdresL1@\n" +
                                    "@Faktura/Podmiot1/Adres/AdresL2@\n" +
                                    "NIP: @Faktura/Podmiot1/DaneIdentyfikacyjne/NIP@"));
    }

    private String transform( String source) {
        final Pattern pattern= Pattern.compile("@(.*?)@([^@]*)");
        final Matcher matcher= pattern.matcher( source);
        final StringBuilder result= new StringBuilder();

        while( matcher.find()) {
            if( result.isEmpty()) {
                if( matcher.start()> 0)
                    result.append( "'").append( source, 0, matcher.start()).append( "' || ");
            }else {
                result.append( " || ");
            }
            result.append( matcher.group(1).replaceAll("([^/]+)", "*[name()='$1']"));
            if( !matcher.group(2).isEmpty())
                result.append( " || '").append( matcher.group(2)).append( "'");
        }

        return (result.isEmpty()? result.append( "'").append(source).append( "'"): result)
                .toString();
    }
}
