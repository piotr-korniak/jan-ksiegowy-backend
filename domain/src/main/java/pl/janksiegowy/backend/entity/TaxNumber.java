package pl.janksiegowy.backend.entity;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class TaxNumber {
    private String number;
    private Country country;

    private final static Pattern PATTERN= Pattern.compile("^([A-Z]{2})?(\\d+)$");

    private TaxNumber( String number, Country country) {
        this.number= number;
        this.country= country;
    }

    public static TaxNumber parse( String taxNumber, Country defaultCountry) {
        if (taxNumber == null || taxNumber.trim().isEmpty()) {
            return new TaxNumber("<no tax number>", defaultCountry);
        }

        Matcher matcher= PATTERN.matcher( taxNumber.replaceAll("[^a-zA-Z0-9]", ""));

        if( matcher.find()) {
            return new TaxNumber( matcher.group( 2),
                    matcher.group( 1)!= null ? Country.valueOf( matcher.group( 1)): defaultCountry);
        } else {
            return new TaxNumber("<no tax number>", defaultCountry);
        }
    }

    @Override public boolean equals( Object o) {
        if (this == o) return true;
        if (!(o instanceof TaxNumber taxNumber)) return false;
        return Objects.equals( number, taxNumber.number) &&
                country == taxNumber.country;
    }

    @Override public int hashCode() {
        return Objects.hash( number, country);
    }

}
