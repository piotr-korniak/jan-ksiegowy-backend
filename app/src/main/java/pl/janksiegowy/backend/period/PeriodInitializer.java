package pl.janksiegowy.backend.period;

import lombok.AllArgsConstructor;
import pl.janksiegowy.backend.period.dto.PeriodDto;

@AllArgsConstructor
public class PeriodInitializer {

    private final PeriodFacade facade;

    public void init( PeriodDto[] initialPeriods) {
        for( PeriodDto period: initialPeriods){
            facade.save( period);
        }
    }
}
