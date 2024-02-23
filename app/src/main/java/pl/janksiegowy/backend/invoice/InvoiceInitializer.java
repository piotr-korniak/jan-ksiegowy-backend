package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodQueryRepository;
import pl.janksiegowy.backend.period.PeriodType;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.InvoiceRegisterQueryRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType.InvoiceRegisterTypeVisitor;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;

import java.util.NoSuchElementException;

@AllArgsConstructor
public class InvoiceInitializer {

    private final SettlementQueryRepository settlements;
    private final InvoiceRegisterQueryRepository registers;
    private final PeriodQueryRepository periods;
    private final EntityQueryRepository entities;
    private final PeriodFacade period;
    private final InvoiceFacade invoice;
    private final DataLoader loader;

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
                    .map( entity-> registers.findByCode( fields[0])
                            .map( register-> register.getType().accept(
                                    new InvoiceRegisterTypeVisitor<InvoiceDto.Proxy>() {
                                        @Override public InvoiceDto.Proxy visitPurchaseRegister() {
                                            return InvoiceDto.create().type( InvoiceType.P);
                                        }
                                        @Override public InvoiceDto.Proxy visitSalesRegister() {
                                            return InvoiceDto.create().type( InvoiceType.S);
                                        }

                                    }).entity( entity)
                                        .register( register)
                                        .number( fields[1])
                                        .invoiceDate( Util.toLocalDate( fields[4])) // Date of sale or receipt
                                        .issueDate( Util.toLocalDate( fields[5]))   // Date of issue or purchase
                                        .dueDate( Util.toLocalDate( fields[6]))     // Date of due;

                            ).orElseThrow( ()-> new NoSuchElementException( "Not found register type: "+ fields[0]))
                    ).orElseThrow( ()-> new NoSuchElementException( "Not found contact with tax number: "+ fields[2]));

            if( periods.findMonthByDate( invoice.getInvoiceDate()).isEmpty())
                period.save( PeriodDto.create()
                        .type( PeriodType.M)
                        .begin( invoice.getInvoiceDate()));

            this.invoice.save( invoice);

        }
    }

}
