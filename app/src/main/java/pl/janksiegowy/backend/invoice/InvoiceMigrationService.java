package pl.janksiegowy.backend.invoice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.entity.EntityQueryRepository;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.invoice.dto.InvoiceDto;
import pl.janksiegowy.backend.period.PeriodFacade;
import pl.janksiegowy.backend.period.PeriodQueryRepository;
import pl.janksiegowy.backend.period.dto.PeriodDto;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterQueryRepository;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType;
import pl.janksiegowy.backend.register.invoice.InvoiceRegisterType.InvoiceRegisterTypeVisitor;
import pl.janksiegowy.backend.finances.settlement.SettlementQueryRepository;
import pl.janksiegowy.backend.shared.DataLoader;
import pl.janksiegowy.backend.shared.Util;
import pl.janksiegowy.backend.shared.financial.PaymentMetod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceMigrationService implements InvoiceRegisterTypeVisitor<InvoiceDto.Proxy> {

    private final SettlementQueryRepository settlements;
    private final InvoiceRegisterQueryRepository registers;
    private final PeriodQueryRepository periods;
    private final EntityQueryRepository entities;
    private final PeriodFacade periodFacade;
    private final InvoiceFacade facade;
    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- MM.yyyy.d");

    private Invoice save( InvoiceDto invoice) {
        //return facade.approve( facade.save( invoice));
        return facade.save( invoice);
    }

    public String init() {
        var total= 0;
        var added= 0;
        final PeriodDto.Proxy period= PeriodDto.create();

        for( String[] fields: loader.readData( "invoices.csv")){

            if( fields[0].startsWith( "---")) {

                Optional.of( periodFacade.findMonthPeriodOrAdd( LocalDate.parse(fields[0] + ".1", formatter)))
                        .ifPresent( monthPeriod-> period.id( monthPeriod.getId()));
                continue;
            }
            total++;

            var taxNumber= fields[2].replaceAll( "[^a-zA-Z0-9]", "");
            var country= Country.PL;
            if (!taxNumber.matches("\\d+")) { // Not only digits
                country= Country.valueOf( taxNumber.substring( 0, 2));
                taxNumber = taxNumber.substring(2);
            }

            if( settlements.existsByNumberAndEntityTaxNumber( fields[1], taxNumber))
                continue;
            added++;

            var invoice= entities.findByCountryAndTypeAndTaxNumber( country, EntityType.C, taxNumber)
                    .map( entity-> registers.findByCode( fields[0])
                            .map( register-> InvoiceRegisterType.valueOf( register.getType())
                                .accept(this)
                                    .entity( entity)
                                    .register( register)
                                    .invoicePeriod( period)
                                    .number( fields[1])
                                    .amount( new BigDecimal( fields[3]))
                                    .issueDate( Util.toLocalDate( fields[4])) // Date of issue           |Date of sale or receipt
                                    .invoiceDate( Util.toLocalDate( fields[5]))        // Date of sale (purchase) |Date of issue or purchase
                                    .dueDate( Util.toLocalDate( fields[6])))         // Date of due
                            .map( proxy-> setPaymentMethod( proxy, fields))
                            .orElseThrow( ()-> new NoSuchElementException( "Not found register type: "+ fields[0])))
                    .orElseThrow( ()-> new NoSuchElementException( "Not found contact with tax number: "+ fields[2]));
/*
            if( periods.findMonthByDate( invoice.getInvoiceDate()).isEmpty())
                periodFacade.save( PeriodDto.create()
                        .type( PeriodType.M)
                        .begin( invoice.getInvoiceDate()));*/
            save( invoice);


        }
        return String.format( "%-40s %16s", "Invoice migration complete, added: ", added+ "/"+ total);
    }

    private InvoiceDto.Proxy setPaymentMethod( InvoiceDto.Proxy proxy, String[] fields) {
        return fields.length> 7? getOrDefaultPaymentMethod( fields[7], proxy): proxy;
    }

    private InvoiceDto.Proxy getOrDefaultPaymentMethod(String value, InvoiceDto.Proxy proxy) {
        try {
            return proxy.paymentMetod( PaymentMetod.valueOf( value));
        } catch (IllegalArgumentException | NullPointerException e) {
            return proxy;
        }
    }

    @Override public InvoiceDto.Proxy visitPurchaseRegister() {
        return InvoiceDto.create().type( InvoiceType.P);
    }
    @Override public InvoiceDto.Proxy visitSalesRegister() {
        return InvoiceDto.create().type( InvoiceType.S);
    }
}
