package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.finances.payment.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class DecreeInitializer {

    private final PaymentDocumentRepository payments;

    public void init() {

        payments.save( (Payment) new Receipt()
                .setDocumentId(  UUID.fromString( "e618b66b-69af-4621-b087-0b5b8f2c7c3b" ))
   //             .setType( PaymentType.R)
                .setDates( LocalDate.now(), LocalDate.now().plusDays( 14))
                .setAmount( BigDecimal.TEN));
/*
        var history = new Object() {
            LocalDate date = LocalDate.EPOCH;
        };

        var lp= 0;
        var decree= new DecreeMap( DecreeDto.create());

        for( String[] fields : loader.readData( "decree.txt" ) ) {
            if( fields[0].startsWith( "--- " ) ) {    // only set date
                history.date= Util.toLocalDate( fields[0].substring(4));
                continue;
            }

            System.err.println( fields[0]+ ":"+ new BigDecimal( fields[1]).setScale( 2));

            decree.add( DecreeLineDto.create()
                    .page( AccountPage.valueOf( fields[0]))
                    .value( new BigDecimal( fields[1]).setScale( 2)));

            if( decree.isBalances()) {
                System.err.println( "Utrwalamy!" );
                System.err.println( "XX: "+ decrees
                        .existsByDocument(  String.format( "XX%05d", ++lp)));
            }

        }*/
    }
}
