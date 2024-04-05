package pl.janksiegowy.backend.shared;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class Util {

    private static final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "dd.MM.yyyy");
    private static final DatatypeFactory datatypeFactory;

    static {
        try {
            datatypeFactory= DatatypeFactory.newInstance();
        } catch ( DatatypeConfigurationException e) {
            throw new RuntimeException( e);
        }
    }

    public static LocalDate toLocalDate( String date) {
        return LocalDate.parse( date.trim(), formatter);
    }
    public static XMLGregorianCalendar toGregorian( LocalDate date) {
        return datatypeFactory.newXMLGregorianCalendar( date.toString());
    }

    public static LocalDate min( LocalDate date1, LocalDate date2) {
        return date1.isBefore( date2)? date1: date2;
    }

    public static LocalDate max( LocalDate date1, LocalDate date2) {
        return date1.isAfter( date2)? date1: date2;
    }

    public static String toString( LocalDate date) {
        return date.format( formatter);
    }

    public static BigDecimal toBigDecimal( String amount, int precision) {
        return new BigDecimal( amount
                .replace("\u00A0", "")  // delete whitespace characters
                .replace(',', '.'))      // replace the comma with a full stop
                .setScale( precision);
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
