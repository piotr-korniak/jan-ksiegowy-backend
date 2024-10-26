package pl.janksiegowy.backend.salary;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class WageIndicatorService {

    // Mapa: typ wskaźnika -> lista wskaźników
    private final Map<WageIndicatorType, List<WageIndicator>> indicators = new HashMap<>();

    @PostConstruct
    public void initIndicators() {
        // Inicjalizacja wskaźników przy użyciu enum
        addIndicator(WageIndicatorType.UZ_MRT,
                LocalDate.of(1970, 1, 1), new BigDecimal("0.0976"));
        addIndicator(WageIndicatorType.KO_UZP,
                LocalDate.of(1970, 1, 1), new BigDecimal("111.25"));
        addIndicator(WageIndicatorType.KO_UZP,
                LocalDate.of(2019, 10, 1), new BigDecimal("250.00"));
        addIndicator(WageIndicatorType.KW_WLN,
                LocalDate.of(1970, 1, 1), new BigDecimal("46.33"));
        addIndicator(WageIndicatorType.KW_WLN,
                LocalDate.of(2019, 10, 1), new BigDecimal("43.76"));
        addIndicator(WageIndicatorType.ST_PDZ,
                LocalDate.of(1970, 1, 1), new BigDecimal("0.18"));
        addIndicator(WageIndicatorType.ST_PDZ,
                LocalDate.of(2019, 10, 1), new BigDecimal("0.17"));
        addIndicator(WageIndicatorType.ST_PDZ,
                LocalDate.of(2022, 10, 1), new BigDecimal("0.12"));
    }

    // Metoda do dodawania wskaźników przy użyciu enum jako klucza
    public void addIndicator(WageIndicatorType code, LocalDate date, BigDecimal value) {
        indicators.computeIfAbsent(code, k -> new ArrayList<>())
                .add(new WageIndicator(code, date, value));
        // Sortowanie listy wskaźników po dacie malejąco
        indicators.get(code).sort((i1, i2) -> i2.getDate().compareTo(i1.getDate()));
    }

    // Pobieranie wskaźnika na podstawie enum i daty
    public Optional<WageIndicator> getWageIndicator(WageIndicatorType code, LocalDate date) {
        return indicators.getOrDefault(code, Collections.emptyList()).stream()
                .filter(indicator -> !date.isBefore(indicator.getDate()))
                .findFirst();
    }

    // Klasa reprezentująca wskaźnik
    @Getter
    @AllArgsConstructor
    public static class WageIndicator {
        private final WageIndicatorType code;
        private final LocalDate date;
        private final BigDecimal value;
    }
}
