package pl.janksiegowy.backend.shared;

import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.shared.financial.TaxNumber;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class Util {

    private static final DateTimeFormatter dd_MM_yyyy= DateTimeFormatter.ofPattern( "dd.MM.yyyy");
    private static final DateTimeFormatter yyyy_MM_dd= DateTimeFormatter.ofPattern( "yyyy-MM-dd");
    private static final DateTimeFormatter yyyy_MM_dd_HH_mm_ss= DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss");
    public static final DecimalFormat N_NNN_NN_0_00= new DecimalFormat( "#,###,##0.00");
    private static final Pattern INVALID_FILENAME_CHARS= Pattern.compile("[^a-zA-Z0-9-_.]");
    private static final DatatypeFactory datatypeFactory;

    static {
        try {
            datatypeFactory= DatatypeFactory.newInstance();
        } catch ( DatatypeConfigurationException e) {
            throw new RuntimeException( e);
        }
    }
    private static Pattern PATTERN= Pattern.compile("^([A-Z]{2})?(\\d+)$");


    public static TaxNumber parseTaxNumber( String taxNumber, String defaultCountry) {
        Matcher matcher= PATTERN.matcher( taxNumber.replaceAll( "[^a-zA-Z0-9]", ""));

        if( matcher.find()) {
            return new TaxNumber() {
                @Override public Country getCountry() {
                    return Country.valueOf( matcher.group(1)!= null? matcher.group(1): defaultCountry);
                }
                @Override public String getTaxNumber() {
                    return matcher.group(2);
                }
            };
        } else {
            return new TaxNumber() {
                @Override public String getTaxNumber() {
                    return "<no tax number>";
                }
                @Override public Country getCountry() {
                    return Country.valueOf( defaultCountry);
                }
            };
        }
    }

    public static LocalDate toLocalDate( String date) {
        return LocalDate.parse( date.trim(), dd_MM_yyyy);
    }
    public static String format( LocalDate date) {
        return date.format( yyyy_MM_dd);
    }

    public static XMLGregorianCalendar toGregorian( LocalDate date) {
        return datatypeFactory.newXMLGregorianCalendar( date.toString());
    }

    public static XMLGregorianCalendar gregorianNow() {
        return datatypeFactory.newXMLGregorianCalendar( new GregorianCalendar());
    }

    public static XMLGregorianCalendar toGregorianYear( LocalDate date) {
        var year= datatypeFactory.newXMLGregorianCalendar();
        year.setYear( date.getYear());
        return year;
    }

    public static XMLGregorianCalendar toGregorianMonth( LocalDate date) {
        var month= datatypeFactory.newXMLGregorianCalendar();
        month.setMonth( date.getMonthValue());
        return month;
    }

    public static LocalDate min( LocalDate date1, LocalDate date2) {
        return date1.isBefore( date2)? date1: date2;
    }

    public static LocalDate max( LocalDate date1, LocalDate date2) {
        return date1.isAfter( date2)? date1: date2;
    }

    public static String toString( LocalDate date) {
        return date.format( dd_MM_yyyy );
    }
    public static String toString(LocalDateTime dateTime) {
        return dateTime.format( yyyy_MM_dd_HH_mm_ss);
    }
    public static String toString( BigDecimal decimal) {
        return N_NNN_NN_0_00.format( decimal);
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

    public static BigDecimal sum( BigDecimal... addends) {
        BigDecimal result= BigDecimal.ZERO;
        for( BigDecimal addend: addends) {
            result= addend!=null? result.add( addend): result;
        }
        return result;
    }

    public static BigInteger sum( BigInteger... addends) {
        BigInteger result= BigInteger.ZERO;
        for( BigInteger addend: addends) {
            result= addend!=null? result.add( addend): result;
        }
        return result;
    }

    public static String sanitizeFileName( String input) {
        if( input== null)
            throw new IllegalArgumentException("Input string cannot be null");

        // Replace all invalid characters with underscores
        return INVALID_FILENAME_CHARS.matcher(input).replaceAll("_");
    }

    public static LocalDate previousMonthEnd( LocalDate date) {
        return date.withDayOfMonth(1).minusDays(1);
    }
}
