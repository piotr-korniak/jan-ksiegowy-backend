package pl.janksiegowy.backend.shared;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class Util {

    private static final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "dd.MM.yyyy");

    public static LocalDate toLocalDate( String date) {
        return LocalDate.parse( date, formatter);
    }

    public static BigDecimal toBigDecimal( String amount, int precision) {
        return new BigDecimal(
            String.format( "%s%0" + precision + "d",            // add zeros after the decimal point
                amount.replace("\u00A0", "")    // delete whitespace characters
                    .replace(',', '.'), 0));     // Replace the comma with a full stop
    }

    public static String toTaxNumber( String taxNumber) {
        return taxNumber.replaceAll( "[^a-zA-Z0-9]", "");
    }

    public static BigInteger toBigIntegerOrNull( BigDecimal value) {
        return value!= null? value.setScale( 0, ROUND_HALF_UP ).toBigInteger(): null;
    }

    public static BigInteger toBigIntegerOrZero( BigDecimal value) {
        return value!= null? value.setScale( 0, ROUND_HALF_UP ).toBigInteger(): BigInteger.ZERO;
    }

    public static BigDecimal addOrAddend( BigDecimal augend, BigDecimal addend) {
        return augend!=null? augend.add( addend): addend;
    }

}
