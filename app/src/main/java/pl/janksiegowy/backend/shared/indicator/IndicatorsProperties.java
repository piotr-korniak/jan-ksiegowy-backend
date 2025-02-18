package pl.janksiegowy.backend.shared.indicator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import pl.janksiegowy.backend.salary.WageIndicatorCode;
import pl.janksiegowy.backend.declaration.CitIndicatorCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Component
@ConfigurationProperties( prefix = "")
public class IndicatorsProperties {

    private Map<String, TreeMap<LocalDate, BigDecimal>> indicators;
    private String greeting;
    private String farewell;

    public IndicatorsProperties() {
        System.err.println("IndicatorsProperties instantiated");
    }

    public void setGreeting( String greeting) {
        System.err.println("IndicatorsProperties setGreeting");
        this.greeting = greeting;
    }

    public void setIndicators(Map<String, Map<LocalDate, BigDecimal>> indicators) {
        System.err.println("IndicatorsProperties setIndicators");
        this.indicators = new HashMap<>();


        if (indicators != null) {
            for (Map.Entry<String, Map<LocalDate, BigDecimal>> entry : indicators.entrySet()) {
                this.indicators.put(entry.getKey(), new TreeMap<>(entry.getValue()));
            }
        }
    }

    public BigDecimal getWageIndicator( WageIndicatorCode code, LocalDate date) {
        return getIndicatorValue( code.toString(), date);
    }

    public BigDecimal getCitIndicator( CitIndicatorCode code, LocalDate date) {
        return getIndicatorValue( code.toString(), date);
    }

    private BigDecimal getIndicatorValue( String code, LocalDate date) {
        return Optional.ofNullable( indicators.get(code))
            .map( dateToValueMap -> Optional.ofNullable( dateToValueMap.floorKey( date))
                .map( dateToValueMap::get)
                .orElseThrow(()-> new IllegalArgumentException( "No indicator value available for the given date: "+ date)))
            .orElseThrow(()-> new IllegalArgumentException( "No indicators found for code: "+ code));

    }
}
