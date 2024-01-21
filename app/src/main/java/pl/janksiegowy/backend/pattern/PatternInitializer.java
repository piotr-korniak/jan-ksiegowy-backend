package pl.janksiegowy.backend.pattern;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.shared.DataLoader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class PatternInitializer {

    private final DataLoader loader;

    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    public void init() {
        var history= new Object(){ LocalDate date= LocalDate.EPOCH;};

        for( String[] fields : loader.readData( "patterns.txt")) {
            if( fields[0].startsWith( "--- " ) ) {    // set date
                history.date= LocalDate.parse( fields[0], formatter);
                continue;
            }


        }
    }
}
