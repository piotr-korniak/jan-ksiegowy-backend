package pl.janksiegowy.backend.accounting.decree;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.accounting.account.AccountPage;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeLineDto;
import pl.janksiegowy.backend.accounting.decree.dto.DecreeMap;
import pl.janksiegowy.backend.finances.payment.*;
import pl.janksiegowy.backend.shared.Util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class DecreeInitializer {

    private final PaymentRepository payments;

    public void init() {
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
