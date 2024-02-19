package pl.janksiegowy.backend.shared;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}
