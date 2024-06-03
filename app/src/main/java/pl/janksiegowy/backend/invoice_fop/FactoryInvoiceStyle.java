package pl.janksiegowy.backend.invoice_fop;

import org.apache.logging.log4j.util.PropertySource;
import pl.janksiegowy.backend.invoice_fop.dto.TemplateFunction;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.fop.*;
import pl.janksiegowy.backend.shared.style.*;

import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FactoryInvoiceStyle {

    public StyleSheet prepare() {
        return new StyleSheet().setTemplates( List.of( new InvoiceRoot() {{
            setRoot( new Root() {{
                fontSize= 10+ "pt";
                fontFamily= "Lato";
                setLayoutMasterSet( new LayoutMasterSet() {{
                    setSimplePageMasterOrPageSequenceMaster( List.of( new SimplePageMaster() {{
                        masterName= "sales-invoice";
                        marginLeft= 20+ "mm";
                        setRegionBody( new RegionBody());
                    }}));
                }});
                setPageSequence( List.of( new PageSequence() {{
                    masterReference= "sales-invoice";
                    setFlow( new Flow() {{
                        flowName= "xsl-region-body";
                        getHeaderFields().forEach( templateFunction-> getMarkerOrBlockOrBlockContainer()
                                    .add( from( templateFunction, blockCreator)
                                            .setPosition( PositionType.ABSOLUTE)));
                        getMarkerOrBlockOrBlockContainer().add( new BlockContainer() {{
                            top= "22mm";
                            left= "2mm";
                            position= PositionType.ABSOLUTE;
                            setMarkerOrBlockOrBlockContainer( List.of( new Block() {{
                                setContent( List.of( new ExternalGraphic() {{
                                    height= 32+ "pt";
                                    width= 32+ "pt";
                                    src= "url('classpath:{$image-directory}/eleutheria.png')";
                                }}));
                            }}));
                        }});
                        getMarkerOrBlockOrBlockContainer().add( new Block() {{
                            spaceBefore= 67+ "mm";
                        }});

                        /* invoice lines */
                        getMarkerOrBlockOrBlockContainer().add( new Table() {{
                            setTableColumn( getColumnsInvoiceLines());
                            setTableHeader( new TableHeader() {{
                                setTableRow( List.of( new TableRow() {{
                                    setTableCell( getHeaderInvoiceLines());
                                }}));
                            }});
                            getTableBody().add( new TableBody() {{
                                this.forEach= new ForEach() {{
                                    select= transform( "@Faktura/Fa/FaWiersz@");
                                    tableRow= new TableRow(){{
                                        setTableCell( getDataInvoiceLines());
                                    }};
                                }};

                            }});
                            getTableBody().add( new TableBody() {{
                                setTableRow( List.of( new TableRow() {{
                                    setTableCell( List.of( new TableCell() {{
                                        getMarkerOrBlockOrBlockContainer().add( new Block());
                                    }}));
                                }}));
                            }});
                        }});

                        getMarkerOrBlockOrBlockContainer().add( new Table() {{
                            border= "0pt solid silver";
                            setTableColumn( List.of(
                                new TableColumn().setColumnWidth( 91+ "mm"),
                                new TableColumn().setColumnWidth( 91+ "mm")));
                            setTableBody( List.of( new TableBody() {{ setTableRow( List.of(
                                new TableRow() {{ setTableCell( List.of(
                                    new TableCell() {{
                                        setMarkerOrBlockOrBlockContainer( List.of( new Block()));
                                    }},
                                    new TableCell() {{ getMarkerOrBlockOrBlockContainer().add( new Table(){{
                                        var razem= invoiceSumColumns.get( 0);
                                        var osobno= invoiceSumColumns.get( 1);
                                        setTableColumn( List.of(
                                            new TableColumn().setColumnWidth( razem.getWidth()+ "mm"),
                                            new TableColumn()));
                                        setTableBody( List.of( new TableBody() {{
                                            setTableRow(List.of(
                                                new TableRow() {{
                                                    setTableCell(List.of(
                                                        new TableCell() {{
                                                            setMarkerOrBlockOrBlockContainer( List.of( new Block() {{
                                                                displayAlign= "after";
                                                                paddingBottom= "0pt";
                                                                borderBottom= "1pt solid #407EB6";
                                                                paddingRight= razem.getPaddingRight()+ "pt";
                                                                if( razem.getFontSize()!= null) setFontSize( razem.getFontSize()+ "pt");
                                                                if( razem.getFontWeight()!=null) setFontWeight( razem.getFontWeight());
                                                                if( razem.getTextAlign()!= null) {
                                                                    setTextAlign( TextAlignType.fromValue( razem.getTextAlign()));
                                                                }
                                                                setContent( List.of( razem.getLabel()));
                                                            }}));
                                                        }},
                                                        new TableCell() {{
                                                            paddingTop= "8pt";
                                                            getMarkerOrBlockOrBlockContainer().add( new Table() {{

                                                                invoiceSumColumns.stream().skip(2)
                                                                        .forEach( column-> getTableColumn().add( new TableColumn(){{
                                                                            setColumnWidth( column.getWidth()+ "mm");
                                                                        }}));
                                                                setTableBody( List.of( new TableBody() {{
                                                                    setTableRow( List.of( new TableRow(){{
                                                                        setTableCell( getInvoiceSums( razem));
                                                                    }}
                                                                    ));
                                                                }}));
                                                            }});
                                                        }}
                                                    ));
                                                }},
                                                    new TableRow() {{
                                                        setTableCell(List.of(
                                                                new TableCell() {{
                                                                    setMarkerOrBlockOrBlockContainer( List.of( new Block() {{
                                                                        displayAlign= "after";
                                                                        padding= "2pt";
                                                                        paddingBottom= "0pt";
                                                                        paddingRight= osobno.getPaddingRight()+ "pt";
                                                                        if( osobno.getFontSize()!= null) setFontSize( osobno.getFontSize()+ "pt");
                                                                        if( osobno.getFontWeight()!=null) setFontWeight( osobno.getFontWeight());
                                                                        if( osobno.getTextAlign()!= null) {
                                                                            setTextAlign( TextAlignType.fromValue( osobno.getTextAlign()));
                                                                        }
                                                                        setContent( List.of( osobno.getLabel()));
                                                                    }}));
                                                                }},
                                                                new TableCell() {{
                                                                    getMarkerOrBlockOrBlockContainer().add( new Table() {{

                                                                        invoiceSumColumns.stream().skip(2)
                                                                                .forEach( column-> getTableColumn().add( new TableColumn(){{
                                                                                    setColumnWidth( column.getWidth()+ "mm");
                                                                                }}));
                                                                        setTableBody( List.of( new TableBody() {{
                                                                            rowIf= new If(){{
                                                                                test= "*[name()='Faktura']/*[name()='Fa']/*[name()='P_13_1'] + " +
                                                                                        "*[name()='Faktura']/*[name()='Fa']/*[name()='P_14_1'] != 0";
                                                                                setTableRow( new TableRow() {{
                                                                                    setTableCell( List.of(
                                                                                        new TableCell() {{
                                                                                            displayAlign= "after";
                                                                                            padding= "2pt";
                                                                                            paddingBottom= "0pt";
                                                                                            borderBottom= "0.5pt solid #407EB6";
                                                                                            getMarkerOrBlockOrBlockContainer().add( new Block() {{

                                                                                                if( osobno.getFontSize()!= null) setFontSize( osobno.getFontSize()+ "pt");
                                                                                                textAlign= TextAlignType.RIGHT;
                                                                                                valueOf= new ValueOf(){{
                                                                                                    select= "format-number( *[name()='Faktura']/" +
                                                                                                            "*[name()='Fa']/*[name()='P_13_1'], '###.##0,00', 'european')";
                                                                                                }};
                                                                                            }});
                                                                                        }},
                                                                                        new TableCell() {{
                                                                                            displayAlign= "after";
                                                                                            padding= "2pt";
                                                                                            paddingBottom= "0pt";
                                                                                            borderBottom= "0.5pt solid #407EB6";
                                                                                            getMarkerOrBlockOrBlockContainer().add( new Block() {{
                                                                                                if( osobno.getFontSize()!= null) setFontSize( osobno.getFontSize()+ "pt");
                                                                                                textAlign= TextAlignType.CENTER;
                                                                                                content= List.of( "23");
                                                                                            }});
                                                                                        }},
                                                                                        new TableCell() {{
                                                                                            displayAlign= "after";
                                                                                            padding= "2pt";
                                                                                            paddingBottom= "0pt";
                                                                                            borderBottom= "0.5pt solid #407EB6";
                                                                                            getMarkerOrBlockOrBlockContainer().add( new Block(){{
                                                                                                if( osobno.getFontSize()!= null) setFontSize( osobno.getFontSize()+ "pt");
                                                                                                textAlign= TextAlignType.RIGHT;
                                                                                                valueOf= new ValueOf(){{
                                                                                                    select= "format-number( *[name()='Faktura']/" +
                                                                                                            "*[name()='Fa']/*[name()='P_14_1'], '###.##0,00', 'european')";
                                                                                                }};
                                                                                            }});
                                                                                        }},
                                                                                        new TableCell() {{
                                                                                            displayAlign= "after";
                                                                                            padding= "2pt";
                                                                                            paddingBottom= "0pt";
                                                                                            borderBottom= "0.5pt solid #407EB6";
                                                                                            getMarkerOrBlockOrBlockContainer().add( new Block(){{
                                                                                                if( osobno.getFontSize()!= null) setFontSize( osobno.getFontSize()+ "pt");
                                                                                                textAlign= TextAlignType.RIGHT;
                                                                                                valueOf= new ValueOf(){{
                                                                                                    select= "format-number( *[name()='Faktura']/*[name()='Fa']/*[name()='P_13_1'] +" +
                                                                                                            "*[name()='Faktura']/*[name()='Fa']/*[name()='P_14_1'], '###.##0,00', 'european')";
                                                                                                }};
                                                                                            }});
                                                                                        }}
                                                                                    ));
                                                                                }});
                                                                            }};
                                                                        }}));
                                                                    }});
                                                                }}
                                                        ));
                                                    }}
                                            ));


                                            }}));
                                    }});
                                    }}
                                    ));
                                }},
                                new TableRow() {{ setTableCell( List.of(
                                        new TableCell() {{
                                            paddingLeft= 5+ "pt";
                                            getPaymentFields().forEach(
                                                    templateFunction->
                                                        getMarkerOrBlockOrBlockContainer()
                                                            .add( from( templateFunction, blockCreator)));
                                            getMarkerOrBlockOrBlockContainer().add( new ForEach() {{
                                                select= transform( "@Faktura/Fa/Platnosc/RachunekBankowy@");
                                                setBlockContainer( new BlockContainer() {{
                                                    fontSize= 8+ "pt";
                                                    paddingTop= 5+ "pt";
                                                    setMarkerOrBlockOrBlockContainer( List.of(
                                                            new Block() {{
                                                                valueOf= new ValueOf(){{
                                                                    select= transform( "Bank: @NazwaBanku@");
                                                                }};
                                                            }},
                                                            new Block() {{
                                                                valueOf= new ValueOf() {{
                                                                    select= "'Nr konta: '";
                                                                }};

                                                                callTemplate= new CallTemplate() {{
                                                                        name= "format-account-number";
                                                                        withParam= new WithParam() {{
                                                                            name= "number";
                                                                            select = "*[name()='NrRB']";
                                                                        }};
                                                                    }};
                                                            }}
                                                    ));
                                                }});
                                            }});
                                        }},
                                        new TableCell() {{
                                            rightPanel.forEach( templateFunction -> getMarkerOrBlockOrBlockContainer()
                                                        .add( from( templateFunction, blockCreator)));
                                        }}
                                    ));
                                }}));
                                }}));
                            }});
                        }});


                    }}));
                }});
            }}));
        }

    private final List<TemplateFunction> rightPanel= List.of(

            TemplateFunction.create().function( "Piotr Korniak")
                    .paddingTop( 44)
                    .fontSize( 8)
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().function( ". ".repeat( 30))
                    .paddingTop( -4)
                    .fontSize( 8)
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().function( "osoba wystawiająca fakturę")
                    .fontSize( 8)
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().function( ". ".repeat( 28))
                    .paddingTop( 44)
                    .fontSize( 8)
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().function( "osoba odbierająca fakturę")
                    .fontSize( 8)
                    .textAlign( TextAlignType.CENTER.value())

    );

    private final List<TemplateFunction> invoiceSumColumns= List.of(
            TemplateFunction.create().label( "Razem:")
                    .width( 20).paddingRight( 10)
                    .fontSize( 9).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.RIGHT.value()),
            TemplateFunction.create().label( "w tym:")
                    .width( 20).paddingRight( 10)
                    .fontSize( 8)
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.RIGHT.value()),
             TemplateFunction.create().width( 20)
                     .function( "format-number( " +
                             "number( concat('0', *[name()='Faktura']/*[name()='Fa']/*[name()='P_13_1'])) + " +
                             "number( concat('0', *[name()='Faktura']/*[name()='Fa']/*[name()='P_13_2'])), " +
                             "'###.##0,00', 'european')")
                     .backgroundColor( "#407EB6").color( "white")
                     .textAlign( TextAlignType.RIGHT.value()),
            TemplateFunction.create().width( 11)
                    .function( "''")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().width( 20)
                    .function( "format-number( " +
                            "number( concat('0', *[name()='Faktura']/*[name()='Fa']/*[name()='P_14_1'])) + " +
                            "number( concat('0', *[name()='Faktura']/*[name()='Fa']/*[name()='P_14_2'])), " +
                            "'###.##0,00', 'european')")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.RIGHT.value()),
            TemplateFunction.create().width( 20)
                    .function( "format-number( *[name()='Faktura']/" +
                            "*[name()='Fa']/*[name()='P_15'], '###.##0,00', 'european')")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.RIGHT.value())
    );

    private final List<TemplateFunction> invoiceLineColumns= List.of(
            TemplateFunction.create().width( 8)
                    .label( "L.p.").function( "@NrWierszaFa@.")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().width( 62)
                    .label( "Nazwa usługi / towaru").function( "@P_7@")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.LEFT.value()).paddingLeft( 2),
            TemplateFunction.create().width( 12)
                    .label( "Ilość").function( "@P_8B@")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().width( 9)
                    .label( "J.m.").function( "@P_8A@")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().width( 20)
                    .label( "Cena\nNetto")
                    .function( "' || format-number( *[name()='P_9A'], '##.###.##0,00', 'european')||'")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .textAlign( TextAlignType.RIGHT.value()).paddingRight( 2),
            TemplateFunction.create().width( 20)
                    .label( "Wartość\nNetto").color( "white")
                    .function( "' || format-number( *[name()='P_11'], '##.###.##0,00', 'european')||'")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6")
                    .textAlign( TextAlignType.RIGHT.value()).paddingRight( 2),
            TemplateFunction.create().width( 11)
                    .label( "Stawka\nVAT").function( "@P_12@")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .paddingLeft( 2).textAlign( TextAlignType.CENTER.value()),
            TemplateFunction.create().width( 20)
                    .label( "Wartość\nVAT")
                    .function( "' || format-number( *[name()='P_11Vat'], '##.###.##0,00', 'european')||'")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .paddingLeft( 2).textAlign( TextAlignType.RIGHT.value()),
            TemplateFunction.create().width( 20)
                    .label( "Wartość\nBrutto")
                    .function( "' || format-number( *[name()='P_11A'], '##.###.##0,00', 'european')||'")
                    .fontSize( 8).fontWeight( "bold")
                    .backgroundColor( "#407EB6").color( "white")
                    .paddingLeft( 2).textAlign( TextAlignType.RIGHT.value())
    );

    private List<TableColumn> getColumnsInvoiceLines() {
        return invoiceLineColumns.stream()
                .map( column-> new TableColumn() {{
                    if( column.getWidth()!= null) setColumnWidth( column.getWidth()+"mm");
                }})
                .collect( Collectors.toList());
    }

    private List<TableCell> getInvoiceSums( TemplateFunction razem) {
        return invoiceSumColumns.stream().skip( 2)
                .map( column-> new TableCell() {{
                    displayAlign= "after";
                    padding= "2pt";
                    paddingBottom= "0pt";
                    borderBottom= "1pt solid #407EB6";
                    if( column.getFunction()!= null) {
                        getMarkerOrBlockOrBlockContainer().add( new Block() {{
                            if( razem.getFontSize()!= null) setFontSize( razem.getFontSize()+ "pt");
                            if( razem.getFontWeight()!= null) setFontWeight( razem.getFontWeight());
                            if( column.getTextAlign()!= null) {
                                setTextAlign( TextAlignType.fromValue( column.getTextAlign()));
                            }
                            setValueOf( new ValueOf() {{
                                select= column.getFunction();
                            }});
                        }});
                    }
                }})
                .collect( Collectors.toList());
    }

    private List<TableCell> getDataInvoiceLines() {
        return invoiceLineColumns.stream()
                .map( column-> new TableCell() {{
                    displayAlign= "after";
                    padding= "2pt";
                    paddingBottom= "0pt";
                    borderBottom="0.5pt solid #407EB6";
                    if( column.getFunction()!= null) {
                        for( String function: column.getFunction().split("\n")) {
                            getMarkerOrBlockOrBlockContainer().add( new Block() {{
                                if( column.getFontSize()!= null) setFontSize( column.getFontSize()+ "pt");
                                if( column.getPaddingLeft()!= null) setPaddingLeft( column.getPaddingLeft()+ "pt");
                                if( column.getTextAlign()!= null) {
                                    setTextAlign( TextAlignType.fromValue( column.getTextAlign()));
                                }
                                setValueOf( new ValueOf() {{
                                    select= transform( function);
                                }});
                            }});
                        }}
                }})
                .collect( Collectors.toList());
    }

    private List<TableCell> getHeaderInvoiceLines() {
        return invoiceLineColumns.stream()
                .map( column-> new TableCell() {{
                    displayAlign= "center";
                    border= "1pt solid white";
                    padding= "2pt";
                    if( column.getColor()!= null) setColor( column.getColor());
                    if( column.getBackgroundColor()!= null) setBackgroundColor( column.getBackgroundColor());
                    if( column.getLabel()!= null) {
                        for( String label: column.getLabel().split("\n")) {
                            getMarkerOrBlockOrBlockContainer().add( new Block() {{
                                if( column.getFontSize()!= null) setFontSize( column.getFontSize()+ "pt");
                                if( column.getFontWeight()!= null) setFontWeight( column.getFontWeight());
                                if( column.getPaddingLeft()!= null) setPaddingLeft( column.getPaddingLeft()+ "pt");
                                setTextAlign( TextAlignType.CENTER);
                                setContent( List.of( label));
                            }});
                    }}
                }})
                .collect( Collectors.toList());
    }

    private List<TemplateFunction> getHeaderFields() {
        return List.of(
                TemplateFunction.create()
                        .top( 22).left( 16).width( 230).fontSize( 14)
                        .function( "Faktura"),
                TemplateFunction.create()
                        .top( 22).left( 35).width( 230).fontSize( 14).fontWeight( "bold")
                        .function( "@Faktura/Fa/P_2@"),
                TemplateFunction.create()
                        .top( 28).left( 16).width( 230).fontSize( 8)
                        .function( "Data wystawienia: ' || format-date( " +
                                "*[name()='Faktura']/*[name()='Fa']/*[name()='P_1'], '[D01].[M01].[Y]') || ', ' || "+
                                "*[name()='Faktura']/*[name()='Fa']/*[name()='P_1M'] ||'"),
                TemplateFunction.create()
                        .top( 31).left( 16).width( 230).fontSize( 8)
                        .function( "Data dostawy/wykonania usługi: ' || format-date( " +
                                "*[name()='Faktura']/*[name()='Fa']/*[name()='P_6'], '[D01].[M01].[Y]') || '"),
                TemplateFunction.create()
                        .code( "EtykietaSprzedawca")
                        .top( 39).left( 2).width( 228)
                        .padding( 2).paddingLeft( 5)
                        .backgroundColor( "#407EB6").color( "white").fontWeight( "bold")
                        .function( "Sprzedawca"),
                TemplateFunction.create()
                        .code( "Sprzedawca")
                        .top( 45).left( 2).width( 230)
                        .paddingLeft( 5).borderBottom( "1pt solid "+ "#407EB6")
                        .function( "\b@Faktura/Podmiot1/DaneIdentyfikacyjne/Nazwa@\n" +
                                    "@Faktura/Podmiot1/Adres/AdresL1@\n" +
                                    "@Faktura/Podmiot1/Adres/AdresL2@\n" +
                                    "NIP: @Faktura/Podmiot1/DaneIdentyfikacyjne/NIP@"),
                TemplateFunction.create()
                        .code( "EtykietaNabywca")
                        .top( 39).left( 101).width( 228)
                        .padding( 2).paddingLeft( 5)
                        .backgroundColor( "#407EB6").color( "white").fontWeight( "bold")
                        .function( "Nabywca"),
                TemplateFunction.create()
                        .code( "Sprzedawca")
                        .top( 45).left( 101).width( 230)
                        .paddingLeft( 5).borderBottom( "1pt solid "+ "#407EB6")
                        .function( "\b@Faktura/Podmiot2/DaneIdentyfikacyjne/Nazwa@\n" +
                                "@Faktura/Podmiot2/Adres/AdresL1@\n" +
                                "@Faktura/Podmiot2/Adres/AdresL2@\n" +
                                "NIP: @Faktura/Podmiot2/DaneIdentyfikacyjne/NIP@")
        );}


    private List<TemplateFunction> getPaymentFields() {
        return List.of(
                TemplateFunction.create()
                        .code( "DoZaplaty")
                        .fontWeight( "bold").fontSize( 10)
                        .function( "Należność ogółem: ' || format-number( *[name()='Faktura']/" +
                                    "*[name()='Fa']/*[name()='P_15'], '###.##0,00', 'european')||',-"),
                TemplateFunction.create()
                        .code( "Slownie")
                        .paddingTop( -1).fontSize( 8)
                        .function( "Słownie: "),
                TemplateFunction.create()
                        .paddingTop( 5).fontSize( 8)
                        .function( "Termin płatności: ' || format-date( *[name()='Faktura']/" +
                                "*[name()='Fa']/*[name()='Platnosc']/*[name()='DataZaplaty'], '[D01].[M01].[Y]') ||'"),
                TemplateFunction.create()
                        .fontSize( 8)
                        .function( "Sposób zapłaty: ' || " +
                        "(if( *[name()='Faktura']/*[name()='Fa']/*[name()='Platnosc']/*[name()='FormaPlatnosci'] = 1)"+
                        " then 'Gotówka' else '') || "+
                        "(if( *[name()='Faktura']/*[name()='Fa']/*[name()='Platnosc']/*[name()='FormaPlatnosci'] = 6)"+
                        " then 'Przelew' else '') ||'")
        );}


    private BlockContainer from( TemplateFunction source,
                                 BiFunction<String, String, Block> blockCreator) {

        return new BlockContainer() {{
            if( source.getLeft()!= null) setLeft( source.getLeft()+ "mm");
            if( source.getTop()!= null) setTop( source.getTop()+ "mm");
            if( source.getWidth()!= null) setWidth( source.getWidth()+ "pt");
            if( source.getColor()!= null) setColor( source.getColor());
            if( source.getBackgroundColor()!= null) setBackgroundColor( source.getBackgroundColor());
            if( source.getFontWeight()!=null) setFontWeight( source.getFontWeight());
            if( source.getPadding()!= null) setPadding( source.getPadding()+ "pt");
            if( source.getPaddingLeft()!= null) setPaddingLeft( source.getPaddingLeft()+ "pt");
            if( source.getPaddingTop()!= null) setPaddingTop( source.getPaddingTop()+ "pt");
            if( source.getFontSize()!= null) setFontSize( source.getFontSize()+ "pt");
            if( source.getBorderBottom()!= null) setBorderBottom( source.getBorderBottom()+ "pt");
            if( source.getTextAlign()!= null) setTextAlign( source.getTextAlign());
            if( source.getFunction()!= null) {
                for( String function: source.getFunction().split("\n")) {
                    getMarkerOrBlockOrBlockContainer()
                            .add( blockCreator.apply(function, source.getCode()));
                }
            }
        }};
    }

    BiFunction<String, String, Block> blockCreator= (function, code)-> {
        return new Block() {{
            if( "Slownie".equals( code))
                callTemplate= new CallTemplate() {{
                    name= "number-to-words";
                    withParam= new WithParam() {{
                        name= "number";
                        //select= "1057.80";
                        select= "*[name()='Faktura']/*[name()='Fa']/*[name()='P_15']";
                    }};
                }};
            if( function.startsWith( "\b"))
                fontWeight= "bold";
            valueOf= new ValueOf().setSelect( transform( function.replaceAll( "\b", "")));
        }};
    };

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
