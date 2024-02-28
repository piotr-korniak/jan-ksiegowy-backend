package pl.janksiegowy.backend.shared.numerator;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Accessors( chain= true)

@Entity
@Table( name= "COUNTERS")
public class Counter {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    private Numerator numerator;

    private int month= -1;
    private int year= -1;

    private String type= "";
    private Long values= 0L;

    @Version
    private long version;

    @Transient
    private final static Pattern pattern= Pattern.compile("%(\\d*)([MNTY])");

    public Counter increment( StringBuffer result) {
        values++;
        Matcher matcher= pattern.matcher( numerator.getMask());

        while( matcher.find()) {
            matcher.appendReplacement( result, generate(
                    matcher.group( 2).charAt( 0),
                    matcher.group( 1).isEmpty()? 0: Integer.parseInt( matcher.group(1))));
        }
        matcher.appendTail( result);
        return this;
    }

    private String generate( char key, int n) {
        return switch (key) {
            case 'Y'-> (year!= -1)? (n== 2? String.format( "%2d", year% 100): String.format( "%4d", year)): "";
            case 'M'-> (month!= -1)? String.format( "%02d", month): "";
            case 'T'-> (n> 0)? String.format( "%"+ n+ "." +n +"s", type): type;
            case 'N'-> (n> 0)? String.format( "%0"+ n+ "d", values): values.toString();
            default-> "";
        };
    }
}
