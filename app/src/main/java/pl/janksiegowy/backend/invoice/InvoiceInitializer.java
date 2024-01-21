package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodQueryRepository;
import pl.janksiegowy.backend.period.PeriodType;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.RegisterQueryRepository;
import pl.janksiegowy.backend.register.RegisterType;
import pl.janksiegowy.backend.register.RegisterType.RegisterTypeVisitor;
import pl.janksiegowy.backend.register.dto.VatRegisterDto;
import pl.janksiegowy.backend.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
public class InvoiceInitializer {

    private final SettlementQueryRepository settlements;
    private final RegisterQueryRepository registers;
    private final PeriodQueryRepository periods;
    private final EntityQueryRepository entities;
    private final PeriodFacade period;
    private final InvoiceFacade invoice;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "dd.MM.yyyy");

    public void init() {
        for( String[] fields: loader.readData( "invoices.txt")){
            if( fields[0].startsWith( "---"))
                continue;

            var taxNumber= fields[2].replaceAll( "[^a-zA-Z0-9]", "");
            var country= Country.PL;
            if (!taxNumber.matches("\\d+")) { // Not only digits
                country= Country.valueOf( taxNumber.substring( 0, 2));
                taxNumber = taxNumber.substring(2);
            }

            if( settlements.existsByNumberAndEntityTaxNumber( fields[1], taxNumber))
                continue;

            var invoice= entities.findByCountryAndTypeAndTaxNumber( country, EntityType.C, taxNumber)
                    .map( entity-> registers.findByCode( VatRegisterDto.class,  fields[0])
                            .map( vatRegister-> vatRegister.getType().accept(
                                    new RegisterTypeVisitor<InvoiceDto.Proxy>() {
                                        @Override public InvoiceDto.Proxy visitPurchaseVatRegister() {
                                            return InvoiceDto.create().type( InvoiceType.S);
                                        }
                                        @Override public InvoiceDto.Proxy visitSalesVatRegister() {
                                            return InvoiceDto.create().type( InvoiceType.C);
                                        }

                                    }).entity( entity)
                                        .vatRegister( vatRegister)
                                        .number( fields[1])
                                        .invoiceDate(
                                                LocalDate.parse( fields[4], formatter)) // Date of sale or receipt
                                        .issueDate(
                                                LocalDate.parse( fields[5], formatter)) // Date of issue or purchase
                                        .dueDate(
                                                LocalDate.parse( fields[6], formatter)) // Date of due;

                            ).orElseThrow( ()-> new NoSuchElementException( "Not found register type: "+ fields[0]))
                    ).orElseThrow( ()-> new NoSuchElementException( "Not found contact with tax number: "+ fields[2]));

            if( periods.findMonthByDate( invoice.getInvoiceDate()).isEmpty())
                period.save( PeriodDto.create()
                            .type( PeriodType.M)
                            .begin( invoice.getInvoiceDate()));

            this.invoice.save( invoice);

        }
    }

    private BigDecimal parse( String kwota, int precyzja){

        System.err.println( "Kwota: "+ kwota);

        StringBuilder sb= new StringBuilder( kwota);
        // dostawiamy zera po przecinku, precyzja dla BigDecimal
        int n= precyzja;
        while( n--> 0)
            sb.append( '0');
        // zamieniamy przecinek na kropke
        n= kwota.lastIndexOf( ',');

        if( n>0){
            sb.replace( n, n+1, ".");
            sb.delete( n+precyzja+1, 100);
        }
        // usuwamy biale znaki
        while((n=sb.indexOf( "\u00A0"))> 0){
            sb.delete( n, n+1);
        }

        return new BigDecimal( sb.toString());
    }

}
